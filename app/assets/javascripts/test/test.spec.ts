import {SomePipe} from 'app/mti';

describe('OrderIdFormatPipe', () => {
    let pipe: SomePipe;

    beforeEach(() => {
        pipe = new SomePipe();
    });

    it('should convert an object to an iterable', () => {
        let obj = {
            property1: 1,
            property2: 'test'
        };

        expect(pipe.transform(obj)).toEqual([
            { key: 'property1', value: 1 },
            { key: 'property2', value: 'test' }
        ]);
    });

    it('should filter falsey values', () => {
        let obj = {
            property1: 1,
            property2: null,
            property3: 'test',
            property4: undefined
        };

        expect(pipe.transform(obj)).toEqual([
            { key: 'property1', value: 1 },
            { key: 'property3', value: 'test' }
        ]);
    });
});
