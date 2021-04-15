import { Role } from "./role";

export class User {
    id: number;
    email: string;
    phoneNumber: string;
    dateOfBirth: any;
    avatar: string;
    gender: string;
    status: string;
    roles: Role[];
    userType: string;
    accessToken?: string;
    typeToken: string;
}