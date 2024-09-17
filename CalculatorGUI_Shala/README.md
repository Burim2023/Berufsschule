<a name="readme-top"></a>
# Calculator GUI Projekt
Author: Burim Shala <br>
LBS Eibiswald | 2aAPC

Dies ist eine einfache GUI-Rechneranwendung, die die Grundlagen der Java-Swing-Bibliothek demonstriert. <br>
Die Anwendung ermöglicht grundlegende arithmetische Operationen wie Addition, Subtraktion, Multiplikation, Division und Prozentrechnung. Sie wurde in IntelliJ mit dem GUI-Designer entwickelt.

## Installation
Um die neueste Version vom Master-Branch zu klonen und die Anwendung zu installieren, folge diesen Schritten:
```cmd

git clone https://github.com/Burim2023/Berufsschule/tree/main/CalculatorGUI_Shala
git checkout origin/master

```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Verwendung
Technologien im Einsatz:
<a href="https://www.java.com" target="_blank" rel="noreferrer"> <img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/java/java-original.svg" alt="java" width="40" height="40"/>
<p>Die Anwendung verwendet Java und die Swing-Bibliothek für das GUI-Design.</p>

```php

public static void main(String[] args) {
    JFrame frame = new JFrame("Calculator");
    frame.setContentPane(new Calculator().calculatorView);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setSize(400,400);
    frame.setVisible(true);
}


```
<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Screenshots

<p>Die Screenshots sind in der abgegebenen PDF vorhanden.</p>

<p align="right">(<a href="#readme-top">back to top</a>)</p>

## Quellen

[Anleitung-GUI](https://examples.javacodegeeks.com/java-development/desktop-java/ide/intellij-gui-designer-example/ "GUI-Anleitung")
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[java.com]: https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white
[java-url]: https://www.java.com/de/
[product-screenshot]: Screenshot.png
