import { Permission } from "./permission.enum";
import { Role } from "./role.enum";

export class User {
    id: number;
    email: string;
    phoneNumber: string;
    dateOfBirth: any;
    avatar: string;
    gender: string;
    status: string;
    roles: Role[];
    permissions: Permission[];
    userType: string;
    accessToken?: string;
    typeToken: string;
}