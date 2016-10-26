package cn.edu.bistu.cs.se.wordapplications;
import java.util.ArrayList;
import java.util.List;
//单词数据库操作接口
public interface WordMethod {
    int addWord(Word word);//添加单词
    int delWord(int wordId);//根据id删除单词
    int delWord(String wordContent);//根据单词内容删除单词
    int updateWord(Word word);//更新单词
    Word getWordById(int wordId);//根据id获取单词
    Word getWordByContent(String word);//根据单词内容获取单词
    List<Word> getAllWords();// 获取所有单词
}
