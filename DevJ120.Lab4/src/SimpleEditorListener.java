/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bezdetk0@mail.ru
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
// Импорт классов, которые используются при решении данной задачи.
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import static javax.swing.JOptionPane.showMessageDialog;

/**
* Класс поддерживает обработку событий WindowEvent и ActionEvent, возникающих в
* классе SimpleEditor.
*
* @author (C)Y.D.Zakovryashin, 11.11.2020
*/
public class SimpleEditorListener extends WindowAdapter
implements ActionListener, AutoCloseable {

/**
* Ссылка на главное окно приложения.
*/
private SimpleEditor editor;

/**
* Ссылка на текущий файл, открытый в приложении.
*/
private File file;

/**
* Ссылки на потоки ввода/вывода, связанные с текущим файлом.
*/
private FileReader reader;
private FileWriter writer;

/**
* Конструктор класса, определяющий ссылку на главное окно приложения.
*
* @param editor ссылка на главное окно приложения.
*/
public SimpleEditorListener(SimpleEditor editor) {
this.editor = editor;
}

/**
* Метод обеспечивает обработку событий ActionEvent, связанных с кнопками и
* пунктами меню класса SimpleEditor.
*
* @param ae ссылка на объект, описывающий событие.
*/

@Override
public void actionPerformed(ActionEvent ae) {
    switch (ae.getActionCommand()) {
    case "open":
// Обработка команды на открытие редактируемого файла. При
// обработке этой команды следует использовать классы:
// java.io.FileReader;
// javax.swing.JFileChooser;
// javax.swing.JOptionPane.
// Следует учесть, что при подаче этой команды в приложении
// уже может быть открыт какой-то файл.
// Все выброшенные исключения в данном фрагменте должны быть
// обработаны, при этом пользователю должны сообщаться причины
// исключения с помощью стандартных диалоговых окон класса
// JOptionPane
            if (file == null){
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Opening a file");
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            int res = fileChooser.showDialog(editor, "Select");
            if (res == JFileChooser.APPROVE_OPTION) {
                file = fileChooser.getSelectedFile();
                try (FileReader fr = new FileReader(file)){
                   char[] buffer = new char[1024];
                   fr.read(buffer);
                   while ( fr.read(buffer) != -1){
                   editor.appendText(new String(buffer), false);
                   buffer = new char[1024];
                        }
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(editor, "File doesnt read", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                 }
            }
            else {
                int input = JOptionPane.showConfirmDialog (null, "File already open. Save chanches?");
                // 0=yes, 1=no, 2=cancel
//                System. out .println(input);
                if (input == 1){
                    
//                    ae.getActionCommand(case:"save");
                }
            }

break;
case "save":
// Обработка команды на сохранение результатов редактирования
// в открытом файле.
if (file !=null)
        try (FileWriter writer = new FileWriter(file, false)) {
            String resultText = editor.getText();
            writer.write(resultText);    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(editor, "File can't be written", "Error", JOptionPane.ERROR_MESSAGE);
        }

    
break;
case "cancel":
// Обработка команды на закрытие открытого файла без сохранения
// сделанных изменений.
    file = null;
    editor.appendText("", true);    
    
break;
case "exit":
// Обработка команды на закрытие приложения. При обработке этой
// команды следует учесть, что в момент её подачи в приложении
// может быть открыт какой-то файл. В этом случае пользователю
// с помощью стандартного диалогового окна класса JOptionPane
// должен предлагаться выбор между закрытием этого файла с
// сохранением или без сохранения сделанных в этом файле
// изменений.
}
}

//private String open (){
//            String text = "";
//            JFileChooser fileChooser = new JFileChooser();
//            fileChooser.setDialogTitle("Opening a file");
//            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//            int res = fileChooser.showDialog(editor, "Select");
//            
//            if (res == JFileChooser.APPROVE_OPTION) {
//            File file = fileChooser.getSelectedFile();
//            
//            try (FileReader reader = new FileReader(file)){
//            char[] buffer = new char[1024];
//                   reader.read(buffer);
//                   while ( reader.read(buffer) != -1){
//                   text += new String(buffer);
//                   buffer = new char[1024];
//
//           }
//                   
//            
//            } catch (IOException ex) {
//                JOptionPane errorPane = new JOptionPane();
//                errorPane.showMessageDialog(null, "Something Went Wrong..", "Error as Title", JOptionPane.ERROR_MESSAGE);
//            }
//            }
//            return text;
//}

/**
* Метод, автоматически вызываемый при удалении объекта из памяти (обычно
* при закрытии приложения). Следует учесть, что в в момент его вызова в
* приложении может оставаться открытым какой-то файл.
*
*/
@Override
public void close() {
throw new UnsupportedOperationException("Not supported yet.");
}
}
