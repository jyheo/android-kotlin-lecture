import 'package:flutter/material.dart';

Future<void> showCustomDialog(context, title, msg) {
  return showDialog(
    context: context,
    barrierDismissible: false, // user must tap button!
    builder: (BuildContext context) {
      return AlertDialog(
        title: Text(title),
        content: Text(msg),
        actions: [
          RaisedButton(
            child: Text('OK'),
            onPressed: () {
              Navigator.of(context).pop();
            },
          ),
        ],
      );
    },
  );
}

void showCustomSnackBar(context, String msg) {
  Scaffold.of(context).showSnackBar(
      SnackBar(content: Text(msg)));
}
