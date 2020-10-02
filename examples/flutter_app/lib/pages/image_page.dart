import 'package:flutter/material.dart';

enum _ImageAsset { image1, image2, image3 }

class _State extends State<ImagePage> {
  var _selection = _ImageAsset.image1;

  _selectedImage() {
    switch(_selection) {
      case _ImageAsset.image1:
        return Image.asset("assets/images/android.png");
      case _ImageAsset.image2:
        return Image.asset("assets/images/android_hsu.png");
      default:
        return Image.network("https://www.hansung.ac.kr/html/themes/www/img/main/logo.gif");
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(title: Text(ImagePage.menu_name),
            actions: [
              PopupMenuButton<_ImageAsset>(
                onSelected: (_ImageAsset result) { setState(() { _selection = result; }); },
                itemBuilder: (BuildContext context) => <PopupMenuEntry<_ImageAsset>>[
                  const PopupMenuItem<_ImageAsset>(
                    value: _ImageAsset.image1,
                    child: Text('Image 1'),
                  ),
                  const PopupMenuItem<_ImageAsset>(
                    value: _ImageAsset.image2,
                    child: Text('Image 2'),
                  ),
                  const PopupMenuItem<_ImageAsset>(
                    value: _ImageAsset.image3,
                    child: Text('Image 3 from Network'),
                  ),
                ],
              )]),
        body: Center(
            child: _selectedImage()));
  }
}

class ImagePage extends StatefulWidget {
  static const nav_url = '/image';
  static const menu_name = 'Image Page';

  @override
  _State createState() => _State();
}
