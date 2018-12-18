import { IMenu } from 'app/shared/model//menu.model';
import { IButton } from 'app/shared/model//button.model';

export interface IMenu {
    id?: number;
    name?: string;
    i18n?: string;
    group?: boolean;
    link?: string;
    linkExact?: boolean;
    externalLink?: string;
    target?: string;
    icon?: string;
    badge?: number;
    badgeDot?: boolean;
    badgeStatus?: string;
    hide?: boolean;
    hideInBreadcrumb?: boolean;
    acl?: string;
    shortcut?: boolean;
    shortcuRoot?: boolean;
    reuse?: boolean;
    sort?: number;
    menu?: IMenu;
    menus?: IMenu[];
    buttons?: IButton[];
    parent?: IMenu;
}

export class Menu implements IMenu {
    constructor(
        public id?: number,
        public name?: string,
        public i18n?: string,
        public group?: boolean,
        public link?: string,
        public linkExact?: boolean,
        public externalLink?: string,
        public target?: string,
        public icon?: string,
        public badge?: number,
        public badgeDot?: boolean,
        public badgeStatus?: string,
        public hide?: boolean,
        public hideInBreadcrumb?: boolean,
        public acl?: string,
        public shortcut?: boolean,
        public shortcuRoot?: boolean,
        public reuse?: boolean,
        public sort?: number,
        public menu?: IMenu,
        public menus?: IMenu[],
        public buttons?: IButton[],
        public parent?: IMenu
    ) {
        this.group = this.group || false;
        this.linkExact = this.linkExact || false;
        this.badgeDot = this.badgeDot || false;
        this.hide = this.hide || false;
        this.hideInBreadcrumb = this.hideInBreadcrumb || false;
        this.shortcut = this.shortcut || false;
        this.shortcuRoot = this.shortcuRoot || false;
        this.reuse = this.reuse || false;
    }
}
