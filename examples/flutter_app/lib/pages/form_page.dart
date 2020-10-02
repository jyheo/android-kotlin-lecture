import 'package:flutter/material.dart';

class _State extends State<FormPage> {
  TextEditingController textEditingController = TextEditingController();
  String displayedText = 'Hello, Flutter';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(FormPage.menu_name)),
        body: Center(
            child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(displayedText),
            TextField(controller: textEditingController),
            RaisedButton(
              child: Text("Change Text!"),
              onPressed: _changeText,
            ),
          ],
        )));
  }

  void _changeText() {
    setState(() {
      displayedText = textEditingController.text;
    });
  }
}

class FormPage extends StatefulWidget {
  static const nav_url = '/form';
  static const menu_name = 'Form Page';

  @override
  _State createState() => _State();
}
