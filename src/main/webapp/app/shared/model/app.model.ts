export interface IApp {
    id?: number;
    name?: string;
    description?: string;
    year?: number;
}

export class App implements IApp {
    constructor(public id?: number, public name?: string, public description?: string, public year?: number) {}
}
