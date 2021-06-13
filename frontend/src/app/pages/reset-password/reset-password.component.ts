import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { User } from 'src/app/_models/user';
import { LoggerService } from 'src/app/_services/logger.service';
import { UserService } from '../../_services/user.service';

@Component({
	selector: 'app-reset-password',
	templateUrl: './reset-password.component.html',
	styleUrls: ['./reset-password.component.css']
})
export class ResetPasswordComponent implements OnInit {

	formData: FormGroup;
	loading = false;
	submitted = false;
	success = '';
	error = '';
	hidePass = true;
	hideConfirmPass = true;
	show = true;
	token: any;

	parentErrorMessage: any;
	parentEmail: any = "";
	currentUser: User;

	constructor(
		private route: ActivatedRoute,
		private formBuilder: FormBuilder,
		private userService: UserService,
		private router: Router,
		private logger: LoggerService
	) {
		this.token = this.route.snapshot.queryParamMap.get('token');
		this.userService.updatePassword(this.token)
			.subscribe({
				next: (res) => {
					this.logger.logger(res);
				},
				error: (err) => {
					this.show = false;
					this.parentErrorMessage = err.message;
				}
			})
	}

	ngOnInit(): void {

		this.formData = this.formBuilder.group({
			password: ['', [Validators.required, Validators.pattern('[a-zA-Z0-9_@]{8,18}')]],
			confirmPassword: ['', Validators.required]
		}, {
			validator: this.MustMatch('password', 'confirmPassword')
		});
	}

	MustMatch(controlName: string, matchingControlName: string) {
		return (formGroup: FormGroup) => {
			const control = formGroup.controls[controlName];
			const matchingControl = formGroup.controls[matchingControlName];

			if (matchingControl.errors && !matchingControl.errors.mustMatch) {
				return;
			}

			if (control.value !== matchingControl.value) {
				matchingControl.setErrors({ mustMatch: true });
			} else {
				matchingControl.setErrors(null);
			}
		}
	}

	get formValid() {
		return this.formData.controls;
	}

	getPasswordErrorMessage(): string {
		if (this.formValid.password.errors.required) {
			return 'Vui lòng nhập mật khẩu của bạn.';
		}
		return this.formValid.password.errors.pattern ? 'Mật khẩu hợp lệ phải có ít nhất 8 ký tự (a-z,A-Z,0-9,_,@).' : '';
	}

	getConfirmPasswordErrorMessage(): string {
		if (this.formValid.confirmPassword.errors.required) {
			return 'Vui lòng nhập lại mật khẩu của bạn.';
		}
		return this.formValid.confirmPassword.errors.mustMatch ? 'Mật khẩu không khớp.' : '';
	}

	onSubmit() {
		this.submitted = true;
		this.loading = true;
		console.log(this.formData.value);

		return this.userService.savePassword(this.token, this.formData.value.password)
			.subscribe({
				next: (result) => {
					this.loading = false;
					this.submitted = false;
					this.success = result.message;
					this.logger.logger(result);
				},
				error: (error) => {
				}
			}),
			setTimeout(() => {
				this.success = '';
				this.router.navigate(['/log-in']);
			}, 3000)
	}

	
}
