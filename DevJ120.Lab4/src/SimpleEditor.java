/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bezdetk0@mail.ru
 */
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
* Класс представляет главное окно приложения, реализующего простой текстовый
* редактор.Класс обеспечивает возможность открытия или создания файла,
* содержащего простой текст, например, в формате .txt, .html или xml, с
* возможностью редактирования его содержания.
*/
public class SimpleEditor extends JFrame {
private Container cp;

/**
* Надпись, отображающая имя текущего/редактируемого файла, а также метку,
* отражающую его текущее состояние. Например, если текст изменён, но не
* сохранён, то в данном поле ставиться специальный маркер (по выбору
* программиста) или меняется шрифт отображения имени файла.
*/  
private JLabel fileName;

/**
* Окно редактирования, в котором отображается и редактируется текст файла.
*/
private JTextArea text;

/**
* Главное меню приложения.
*/
private JMenuBar bar;

/**
* Массив пунктов главного меню приложения, который должен включать "File" и
* "Edit".
*/
private JMenu[] menu;

/**
* Массив команд главного меню приложения, который должен включать команды
* "Open", "Save", "Cancel" и "Exit".
*/
private JMenuItem[] commandMenu;

/**
* Массив кнопок интерфейса "Open", "Save", "Cancel" и "Exit", которые
* дублируют команды главного меню приложения .
*/
private JButton[] commandButton;

/**
* Ссылка на обработчик команд пользовательского интерфейса.
*/
private SimpleEditorListener listener;

/**
* Конструктор приложения.
*/
protected SimpleEditor() {
    setTitle("Simple text editor");
    setSize(800,600);
    setLocation(50, 50);
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    init();
    createMenu();
    setVisible(true);
}

/**
* Стартовый метод приложения.
*
* @param args параметры командной строки.
*/
public static void main(String[] args) {
SimpleEditor simpleEditor = new SimpleEditor();
}

/**
* Метод инициализирует обработчик событий listener, создаёт и настраивает
* все элементы пользовательского интерфейса.
*/
private void init() {
// Реализация метода
    cp=getContentPane();
    cp.setLayout(new BorderLayout());
    JPanel buttonsPanel= new JPanel(new GridLayout(1, 4, 5, 0));
//    JPanel buttonsPanel= new JPanel(new FlowLayout(FlowLayout.CENTER));
    cp.add(buttonsPanel, BorderLayout.NORTH);
    
    listener = new SimpleEditorListener(this);    
    
    
    commandButton = new JButton[]{
        new JButton("Open"),
        new JButton("Save"),
        new JButton("Save as"),
        new JButton("Cancel"),
        new JButton("Exit")
    };
    for (JButton c : commandButton){
            buttonsPanel.add(c);
            }
    commandButton[0].addActionListener(listener);
    commandButton[0].setActionCommand("open");
    commandButton[1].addActionListener(listener);
    commandButton[1].setActionCommand("save");
    commandButton[2].addActionListener(listener);
    commandButton[2].setActionCommand("saveAs");
    commandButton[3].addActionListener(listener);
    commandButton[3].setActionCommand("cancel");
    commandButton[4].addActionListener(listener);
    commandButton[4].setActionCommand("exit");    
    
    fileName = new JLabel();
    fileName.setText("New file");
    cp.add(fileName, BorderLayout.SOUTH);


    text = new JTextArea();
    cp.add(new JScrollPane(text), BorderLayout.CENTER);
    createMenu();
    }

/**
* Метод полностью создаёт главное меню приложения и добавляет его в главное
* окно приложения.
*/
private void createMenu() {
// Реализация метода
    bar = new JMenuBar();
    menu = new JMenu[] {
        new JMenu("File"),
        new JMenu("Edit")};
    for (JMenu m : menu){
                bar.add(m);
                }

    commandMenu = new JMenuItem[]{
        new JMenuItem("Open"),
        new JMenuItem("Save"),
        new JMenuItem("Save as"),        
        new JMenuItem("Cancel"),
        new JMenuItem("Exit")
    };

    commandMenu[0].addActionListener(listener);
    commandMenu[0].setActionCommand("open");
    commandMenu[1].addActionListener(listener);
    commandMenu[1].setActionCommand("save");
    commandMenu[2].addActionListener(listener);
    commandMenu[2].setActionCommand("saveAs");
    commandMenu[3].addActionListener(listener);
    commandMenu[3].setActionCommand("cancel");
    commandMenu[4].addActionListener(listener);
    commandMenu[4].setActionCommand("exit");    
    
    
    menu[0].add(commandMenu[0]);
    menu[0].add(commandMenu[4]);
    menu[1].add(commandMenu[1]);
    menu[1].add(commandMenu[2]);
    menu[1].add(commandMenu[3]);

    setJMenuBar(bar);
}

/**
* Метод обеспечивает добавление или замену текста в окне редактирования.
*
* @param str добавляемый текст.
* @param append значение true означает, что текст добавляется к тексту,
* который содержится в поле. Значение false означает, что текст в поле
* полностью заменяется на значение str.
*/
// Реализация метода
void appendText(String str, boolean append) {
        if (append) text.setText("");
        text.append(str);
}

/**
* Метод возвращает весь текст, содержащийся в окне редактирования.
*
* @return текст из окна редактирования.
*/
String getText() {
// Реализация метода

return text.getText();
}

    public void setFileName(String name) {
       fileName.setText(name);
    }


}