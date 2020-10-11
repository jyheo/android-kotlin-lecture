import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_app/pages/image_page.dart';
import 'package:flutter_app/pages/change_name.dart';
import 'package:flutter_app/pages/form_page.dart';
import 'package:flutter_app/pages/list_page.dart';
import 'package:flutter_app/app_state.dart';
import 'package:flutter_app/ui_utils.dart';

class _Drawer extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Drawer(
      child: ListView(
        padding: EdgeInsets.zero,
        children: <Widget>[
          DrawerHeader(
            decoration: BoxDecoration(
              color: Colors.blue,
            ),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [Text(
              'My Flutter Examples',
              style: TextStyle(
                color: Colors.white,
                fontSize: 24,
              ),
            ),
              Consumer<AppState>(
                  builder: (context, appState, child) => Text(appState.currentUser))
              ]
            )
          ),
          ListTile(
              leading: Icon(Icons.account_circle),
              title: Text(ChangeNamePage.menu_name),
              onTap: () => Navigator.popAndPushNamed(context, ChangeNamePage.nav_url)),
          ListTile(
              leading: Icon(Icons.settings),
              title: Text(FormPage.menu_name),
              onTap: () => Navigator.popAndPushNamed(context, FormPage.nav_url)),
          ListTile(
              leading: Icon(Icons.image),
              title: Text(ImagePage.menu_name),
              onTap: () => Navigator.popAndPushNamed(context, ImagePage.nav_url)),
          ListTile(
              leading: Icon(Icons.list),
              title: Text(ListPage.menu_name),
              onTap: () => Navigator.popAndPushNamed(context, ListPage.nav_url)),
          ListTile(
              leading: Icon(Icons.clear),
              title: Text('close drawer'),
              onTap: () => Navigator.pop(context)),
        ],
      ),
    );
  }
}



class _ScaffoldBody extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
                child: Text('Show SnackBar'),
                onPressed: () => showCustomSnackBar(context, 'Hi! I am a SnackBar.')
            ),
            ElevatedButton(
                child: Text('Show AlertDialog'),
                onPressed: () => showCustomDialog(context, "Alert Dialog", "This is the message of this Dialog.")
            ),
          ],
        ));
  }
}

class StartPage extends StatelessWidget {
  static const nav_url = '/';
  static const menu_name = 'Start Page';

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(menu_name)),
        drawer: _Drawer(),
        body: _ScaffoldBody()
    );
  }
}