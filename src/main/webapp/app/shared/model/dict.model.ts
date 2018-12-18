import { IDictType } from 'app/shared/model//dict-type.model';

export interface IDict {
    id?: number;
    name?: string;
    type?: string;
    dictTypes?: IDictType[];
}

export class Dict implements IDict {
    constructor(public id?: number, public name?: string, public type?: string, public dictTypes?: IDictType[]) {}
}
