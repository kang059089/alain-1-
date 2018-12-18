import { IMenu } from 'app/shared/model//menu.model';

export interface IButton {
    id?: number;
    name?: string;
    acl?: string;
    description?: string;
    sort?: number;
    menu?: IMenu;
    menuParent?: IMenu;
}

export class Button implements IButton {
    constructor(
        public id?: number,
        public name?: string,
        public acl?: string,
        public description?: string,
        public sort?: number,
        public menu?: IMenu,
        public menuParent?: IMenu
    ) {}
}
