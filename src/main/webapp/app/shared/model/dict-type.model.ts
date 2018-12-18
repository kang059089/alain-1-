import { IDict } from 'app/shared/model//dict.model';

export interface IDictType {
    id?: number;
    name?: string;
    code?: string;
    sort?: number;
    dict?: IDict;
    dictParent?: IDict;
}

export class DictType implements IDictType {
    constructor(
        public id?: number,
        public name?: string,
        public code?: string,
        public sort?: number,
        public dict?: IDict,
        public dictParent?: IDict
    ) {}
}
