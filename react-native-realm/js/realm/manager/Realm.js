import Realm from 'realm';
import {ExampleSchema} from "../schema/Schema";

const realm = new Realm({
    schema: [ExampleSchema],
});

export function getExample() {
    return realm.objects('Example');
}

export function putExample(example) {
    realm.write(() => {
        realm.create('Example', example);
    });
}

export function updateExample(example) {
    realm.write(() => {
        realm.create('Example', example, true);
    });
}

export function deleteExample(example) {
    realm.write(() => {
        realm.delete(example);
    });
}
