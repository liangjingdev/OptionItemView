# OptionItemView
快速构建常用的选项条目布局控件！

## 使用方法：
* 1、
```javascript

	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
* 2、
```javascript
dependencies {
	        compile 'com.github.liangjingdev:OptionItemView:1bf5410820'
	}
```

## 1、简述
在项目开发中经常遇到类似“设置”功能的需求，一般都要做一些简单的选项条目布局，一个条目可以会用到布局+多个控件（文本或图标），
一个条目在xml中布局还算可以接受了，但如果一个设置界面的选项条目有10个，还是一样的布局，这样就显示特别臃肿了，而一般一个界面的选项条目有3个以上。
本项目就是为了解决上述问题，满足常规的选项条目布局需求.
* 效果图<br>
![效果图](https://github.com/liangjingdev/OptionItemView/raw/master/img/1.png)<br>
![效果图](https://github.com/liangjingdev/OptionItemView/raw/master/img/3.png)

## 2、使用

* 1、在布局中使用（属性可选，不设置则不显示）：
一般图标的高度是控件高度的一半，所以没有做该自定义属性，写死了。
```javascript
<com.liangjing.library.OptionItemView
     android:id="@+id/oiv"
     android:layout_width="match_parent"
     android:layout_height="60dp"
     android:background="#abcdef"
     app:left_image_margin_left="20dp"
     app:left_image_margin_right="20dp"
     app:left_src="@mipmap/ic_launcher"
     app:left_text="left"
     app:left_text_color="#f00"
     app:left_text_margin_left="20dp"
     app:left_text_size="15sp"
     app:right_image_margin_left="20dp"
     app:right_image_margin_right="20dp"
     app:right_src="@mipmap/ic_launcher"
     app:right_text="right"
     app:right_text_color="#f00"
     app:right_text_margin_right="20dp"
     app:splite_mode="true"
     app:right_text_size="16sp"
     app:title="title"
     app:title_color="#00f"/>
```

*2、点击事件<br>
1）拆分模式<br>
```javascript
需要设置split_mode=true，默认是false
oiv.setOnOptionItemClickListener(new OptionItemView.OnOptionItemClickListener() {
    @Override
    public void leftOnClick() {
        Toast.makeText(getApplicationContext(), "左边被点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void centerOnClick() {
        Toast.makeText(getApplicationContext(), "中间被点击", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void rightOnClick() {
        Toast.makeText(getApplicationContext(), "右边被点击", Toast.LENGTH_SHORT).show();
    }
});
```

2）整体模式<br>
需要设置split_mode=false，默认是false
```javascript
oiv.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Toast.makeText(getApplicationContext(), "整体被点击", Toast.LENGTH_SHORT).show();
    }
});
```

* 3、代码动态修改属性
```javascrip
setTitleText(String text)
setTitleText(int stringId)
setTitleColor(int color)
setTitleSize(int sp)

setLeftText(String text)
setLeftText(int stringId)
setLeftTextSize(int sp)
setLeftTextColor(int color)
setLeftTextMarginLeft(int dp)
setLeftImageMarginLeft(int dp)
setLeftImageMarginRight(int dp)
setLeftImage(Bitmap bitmap)
showLeftImg(boolean flag)
showLeftText(boolean flag)

setRightImage(Bitmap bitmap)
setRightText(String text)
setRightText(int stringId)
setRightTextColor(int color)
setRightTextSize(int sp)
setRightTextMarginRight(int dp)
setRightImageMarginLeft(int dp)
setRightImageMarginRight(int dp)
showRightImg(boolean flag)
showRightText(boolean flag)

setSpliteMode(boolean spliteMode)
getSpliteMode()
```

## 3、解释
![解释](https://github.com/liangjingdev/OptionItemView/raw/master/img/2.png)
