export class User {
    id: string;
    username: string;
    password: string;
    admin: boolean;
    data: boolean;
    analyst: boolean;

    constructor(i: string, u: string, p:string, ad: boolean, d: boolean, an: boolean) {
        this.id = i,
        this.username = u;
        this.password = p;
        this.admin = ad;
        this.data = d;
        this.analyst = an;
    }
}
