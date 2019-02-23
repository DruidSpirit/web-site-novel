//package com.druidelf.novelmain;
//
//import com.mongodb.MongoClient;
//import com.mongodb.client.FindIterable;
//import com.mongodb.client.MongoCursor;
//import com.mongodb.client.MongoDatabase;
//import org.apache.commons.io.FileUtils;
//import org.bson.Document;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.regex.Pattern;
//
//public class MongoDBJDBCTest {
//    public static void main( String args[] ){
//        try{
//            // 连接到 mongodb 服务
//            MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
//
//            // 连接到数据库
//            MongoDatabase mongoDatabase = mongoClient.getDatabase("test");
//            // mongoDbFind(mongoDatabase);
//            mongoDbinsert( mongoDatabase );
//            System.out.println("Connect to database successfully");
//
//        }catch(Exception e){
//            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
//        }
//    }
//    public static void mongoDbFind( MongoDatabase mongoDatabase ) {
//        FindIterable<Document> stus = mongoDatabase.getCollection("stus").find();
//        MongoCursor<Document> iterator = stus.iterator();
//        if (iterator.hasNext()){
//            Object object = iterator.next();
//            System.out.println(((Document) object).toJson());
//        }
//    }
//    public static void mongoDbinsert( MongoDatabase mongoDatabase ) throws IOException {
//
//        File file = new File("C:/Users/wang/Desktop/超级基因优化液.txt");
//        String textNovel = FileUtils.readFileToString(file,"utf-8");
//        Document document = new Document("testNovel",textNovel);
//        List<Document> list = new ArrayList<>();
//        list.add(document);
//        mongoDatabase.getCollection("stus").insertMany(list);
//    }
//}
