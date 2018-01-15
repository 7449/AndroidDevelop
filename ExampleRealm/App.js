import React, {Component} from 'react';
import {
    StyleSheet,
    Text,
    View,
} from 'react-native';
import {getExample, putExample} from "./js/realm/manager/Realm";

export default class App extends Component<{}> {
    render() {
        putExample({example: 'example'});
        return (
            <View style={styles.container}>
                <Text>{'Realm Example表个数  ' + getExample().length}</Text>
            </View>
        );
    }
}

const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center',
        backgroundColor: '#F5FCFF',
    },
    instructions: {
        marginTop: 30,
        textAlign: 'center',
        color: '#333333',
        marginBottom: 5,
    },
});
