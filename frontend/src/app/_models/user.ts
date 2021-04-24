import { Permission } from "./permission.enum";
import { Role } from "./role.enum";

export class User {
    avatar: string;
    dateOfBirth: any;
    email: string;
    fullName: string;
    gender: string;
    id: number;
    permissions: Permission[];
    phoneNumber: string;
    roles: Role[];
    status: string;
    token?: string;
    type: string;
    userType: string;
}