import { IRole } from 'app/shared/model//role.model';

export interface IRole {
    id?: number;
    name?: string;
    acl?: string;
    role?: IRole;
    roles?: IRole[];
    parent?: IRole;
}

export class Role implements IRole {
    constructor(
        public id?: number,
        public name?: string,
        public acl?: string,
        public role?: IRole,
        public roles?: IRole[],
        public parent?: IRole
    ) {}
}
