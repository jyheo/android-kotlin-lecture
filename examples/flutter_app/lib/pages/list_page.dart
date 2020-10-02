import 'package:flutter/material.dart';
import 'package:flutter_app/ui_utils.dart';

_tile(context, icon, title) => ListTile(
  leading: Icon(icon),
  title: Text(title),
  subtitle: Text('subtitle $title'),
  onTap: () => showCustomDialog(context, "Item", title),
);

class _ScaffoldBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    var list = <Widget>[];
    for (var i = 0; i < 20; i++) {
      list.add(_tile(context, Icons.access_alarm, "Item $i"));
    }
    return ListView(
      children: list,
    );
  }
}

class ListPage extends StatelessWidget {
  static const nav_url = '/list_view';
  static const menu_name = 'ListView Page';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(menu_name)),
        body: _ScaffoldBody()
    );
  }
}