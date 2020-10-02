import 'package:flutter/material.dart';
import 'package:provider/provider.dart';
import 'package:flutter_app/pages/start_page.dart';
import 'package:flutter_app/pages/image_page.dart';
import 'package:flutter_app/pages/change_name.dart';
import 'package:flutter_app/pages/form_page.dart';
import 'package:flutter_app/pages/list_page.dart';
import 'package:flutter_app/app_state.dart';



class MyApp extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Navigation Test',
      initialRoute: StartPage.nav_url,
      routes: {
        StartPage.nav_url: (context) => StartPage(),
        ChangeNamePage.nav_url: (context) => ChangeNamePage(),
        FormPage.nav_url: (context) => FormPage(),
        ImagePage.nav_url: (context) => ImagePage(),
        ListPage.nav_url: (context) => ListPage(),
      },
    );
  }
}

void main() => runApp(ChangeNotifierProvider(
    // MyApp() 포함하여 자손 위젯들에게 AppState()를 제공
    create: (context) => AppState(),
    child: MyApp()));
