import { IDictType } from 'app/shared/model//dict-type.model';
import { IOrg } from 'app/shared/model//org.model';

export interface IOrg {
    id?: number;
    name?: string;
    code?: string;
    telephone?: string;
    fax?: string;
    address?: string;
    longitude?: number;
    latitude?: number;
    icon?: string;
    description?: string;
    sort?: number;
    type?: IDictType;
    org?: IOrg;
    orgs?: IOrg[];
    parent?: IOrg;
}

export class Org implements IOrg {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public telephone?: string,
        public fax?: string,
        public address?: string,
        public longitude?: number,
        public latitude?: number,
        public icon?: string,
        public description?: string,
        public sort?: number,
        public type?: IDictType,
        public org?: IOrg,
        public orgs?: IOrg[],
        public parent?: IOrg
    ) {}
}
