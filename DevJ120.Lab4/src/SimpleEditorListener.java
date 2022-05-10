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

private final String[][] FILTERS = { {"txt", "Text document(*.txt)"},
                                    {"docx", "Word files (*.docx)"}
                                    };

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

    open();    
    break;

case "save":
// Обработка команды на сохранение результатов редактирования
// в открытом файле.
    save();
break;

case "saveAs":
    saveAs();
    break;
case "cancel":
// Обработка команды на закрытие открытого файла без сохранения
// сделанных изменений.
    cancel();
    
break;
case "exit":
    exit();
// Обработка команды на закрытие приложения. При обработке этой
// команды следует учесть, что в момент её подачи в приложении
// может быть открыт какой-то файл. В этом случае пользователю
// с помощью стандартного диалогового окна класса JOptionPane
// должен предлагаться выбор между закрытием этого файла с
// сохранением или без сохранения сделанных в этом файле
// изменений.
}
}

private void open(){
            if (file == null && editor.getText().isEmpty()){
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
            editor.setFileName(file.getName());
            }
            else {
                int input = JOptionPane.showConfirmDialog (null, "Save chanches to file?");
                // 0=yes, 1=no, 2=cancel
//                System. out .println(input);
                if (input == 0){
                    save();
                    cancel();
                    open();
                    }
                if (input == 1){
                    cancel();
                    open();
                }
                }
}

private void save(){
    if (file == null || !editor.getText().isEmpty()) {
        saveAs();
    }
    else {
        try (FileWriter fw = new FileWriter(file)) {
            String resultText = editor.getText();
            fw.write(resultText);    
        } catch (IOException e) {
            JOptionPane.showMessageDialog(editor, "File can't be written", "Error", JOptionPane.ERROR_MESSAGE);
        }
        }
 }



private void saveAs(){
    JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Saving file");
        for (int i = 0; i < FILTERS[0].length; i++) {
            customFileFilter ff = new customFileFilter(FILTERS[i][0], 
                                                  FILTERS[i][1]);
            fileChooser.addChoosableFileFilter(ff);
        }
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int res = fileChooser.showSaveDialog(editor);
        if (res == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
        }
        try (FileWriter fw = new FileWriter(file)) {
            String resultText = editor.getText();
            fw.write(resultText);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(editor, "File can't be written", "Error", JOptionPane.ERROR_MESSAGE);
        }
        editor.setFileName(file.getName());
        }
    

private void cancel(){
    file = null;
    editor.appendText("", true);
}

private void exit(){
    if (file != null || !editor.getText().isEmpty()){
        int input = JOptionPane.showConfirmDialog (null, "Save chanches to file?");
        // 0=yes, 1=no, 2=cancel
        if (input == 0) {
            save();
            editor.dispose();
        }
        if (input == 1){
        editor.dispose();
        }
    }
    else {
        editor.dispose();
        }
    
}




/**
* Метод, автоматически вызываемый при удалении объекта из памяти (обычно
* при закрытии приложения). Следует учесть, что в в момент его вызова в
* приложении может оставаться открытым какой-то файл.
*
*/
@Override
public void close() {
if (file.exists() || file!=null){
file.deleteOnExit();
}
throw new UnsupportedOperationException("Not supported yet.");
}
}
