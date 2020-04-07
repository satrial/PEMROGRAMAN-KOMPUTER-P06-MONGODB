/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package produk;

import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import static com.mongodb.client.model.Filters.eq;
import com.mongodb.client.result.UpdateResult;
import java.util.Date;
import org.bson.Document;
import org.bson.types.ObjectId;

/**
 *
 * @author indriawan
 */
public class TestDB {

    public static void main(String[] args) {
        try {
           //koneksi database Mongo db
            MongoDatabase database = Koneksi.sambungDB();
        
            //Melihat daftar koleksi (tabe)
            System.out.println("=================================");
            System.out.println("Daftar table dalam Database");
            MongoIterable<String> tables = database.listCollectionNames();
            for (String name :tables){
                System.out.println(name);
            }
        
         // menabah data 
            System.out.println("=================================");
            System.out.println("Menambahkan data");
            MongoCollection<Document> col = database.getCollection("produk");
            Document doc = new Document();
            doc.put("nama", "Printer InkJet");
            doc.put("harga", 1204000);
            doc.put("tanggal", new Date());
            col.insertOne(doc);
            System.out.println("Data telah disimpan dalam koleksi");
            
            //mendapat _id dati dokumen yg baru saja di insert
            ObjectId id = new ObjectId(doc.get("_id").toString());
            
            //melihat /enampilkan data
            System.out.println("===================================");
            System.out.println("Data dalam koleksi Produksi");
            MongoCursor<Document> cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
        
            //Mencari dokumen berdasarkan id
            Document myDoc = col.find(eq("_id", id)).first();
            System.out.println("==============================");
            System.out.println("Pencarian Data berdasarkan id: "+id);
            System.out.println(myDoc.toJson());
            
            //mengedit data
            Document docs = new Document();
            docs.put("nama", "Canon");
            Document doc_edit = new Document("$set", docs);
            UpdateResult hasil_edit = col.updateOne(eq("_id", id), doc_edit);
            System.out.println(hasil_edit.getModifiedCount());
            
            //Melihat /menampilkan data
            System.out.println("===================================");
            System.out.println("Data dalam koleksi Produksi");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
                 
            //Menghapus data
            col.deleteOne(eq("_id", id));
            //melihat /menampilkan data
            System.out.println("===================================");
            System.out.println("Data dalam koleksi Produksi");
            cursor = col.find().iterator();
            while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
            }
            
        } catch (Exception e) {
        }
    }

}
