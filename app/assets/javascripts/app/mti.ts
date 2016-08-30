import {Pipe, PipeTransform} from '@angular/core';

@Pipe({ name: 'SomePipe' })
export class SomePipe implements PipeTransform {
    transform(obj: any): {key: string, value: any}[] {
        let map = [];
        for (let key in obj) {
            if (obj[key]) {
                map.push({key, value: obj[key]});
            }
        }

        return map;
    }
}
