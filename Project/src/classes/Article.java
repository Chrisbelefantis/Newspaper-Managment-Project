/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package classes;

import java.util.*;
import java.text.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.swing.JOptionPane;



/**
 *
 * @author Ηλιάνα
 */

public class Article{

	private int magazineNum;
	private String newspaper;
        private String title;
	private String path;
	private int length;
        private String keywords=null;
	private String comments= null;
	private String category;
        private String summary = null;
        private String author; 
        private String extraAuthor=null;
        private String picturePath1=null;
        private String picturePath2=null;
        //State and position are setted via the placeArticle method.
        Connection con;

 

        //Constructor used in new Article submission
	public Article(String newspaper,int magazineNum,String author,Connection con){
                
                this.newspaper = newspaper;
		this.magazineNum = magazineNum;
                this.author = author;
                this.con = con;
               
        }

      
       
        
        
        public static String availableSpace(String newspaper,int magazineNum,Connection con){
           
             String query = "{CALL magazineInfo(?,?)}";
             CallableStatement stmt=null;
             ResultSet rs=null;
             
             try{   
               
                stmt = (CallableStatement) con.prepareCall(query);
                stmt.setInt(1,magazineNum);
                stmt.setString(2,newspaper);
                
                boolean isResultSet =stmt.execute();
                
               while(true){
                   
                   if(isResultSet){
                       rs =(ResultSet) stmt.getResultSet();
                       rs.next();
                       ResultSetMetaData rsmd = (ResultSetMetaData)rs.getMetaData();
                   
                       if(rsmd.getColumnCount()==2){
                           
                           return rs.getString(2);
                       }
                       rs.close();
                   }
                   else{
                       if(stmt.getUpdateCount()==-1){
                           break;
                       }
                   }
                   
                   isResultSet = stmt.getMoreResults();
                   
               }
                
               return "No more space";
                
                
                
                
             }
             catch(SQLException e)
             {
                 
                System.out.println( e.getMessage());
                 return e.getMessage();
                 
                 
             }
             finally{
                try{
                    
                if(stmt!=null) {   
                 stmt.close();
                }
                if(rs!=null) {   
                 rs.close();
                }
                
                }
                catch(SQLException e)  {e.getMessage();}
                
            }
             
             
             
             
    
        }

	public void setPath(String path){
            
            this.path=path;
            
        }

	public void setLenght(int lengh){
            
            this.length = lengh;
            
        }


	public void setComments(String comments){
        
            this.comments = comments;
        }

	public void setCategory(String category){
            
            Statement  stmt=null;
            ResultSet  rs=null;
            
            try{
                
                stmt = con.createStatement();
                rs=stmt.executeQuery("Select id from category where categoryTitle='"+category+"'");
                
                
                if(rs.next()){
                
                    this.category = rs.getString(1);
                    
                }
                         
                
            }
            catch(SQLException e){
                
                e.getMessage();
                System.out.println("We had a problem!");
            }
            
            finally{
                try{
                    
                if(stmt!=null) {   
                 stmt.close();
                }
                 if(rs!=null) {   
                 rs.close();
                }
                
                }
                catch(SQLException e)  {e.getMessage();}
                
            }
            

        
        }

        public void setExtraAuthor(String extraAuthor){
            
            this.extraAuthor = extraAuthor;
            
            
        }
        
        
         public void setTitle(String title){
            
            this.title = title;
            
            
        }
         
         
          public void setKeywords(String keywords){
            
            this.keywords = keywords;
            
            
        }
          
           public void setSummary(String summary){
            
            this.summary = summary;
            
            
        }
        
            public void setPicture(String picturePath1){
              
              this.picturePath1 = picturePath1;
              
          }
             
           public void setPicture(String picturePath1,String picturePath2){
              
              this.picturePath1 = picturePath1;
              this.picturePath2 =picturePath2;
              
          }
           
        
        
          
       public void getInfo(){
           
           //System.out.println(magazineNum);
           System.out.println(newspaper);
           //System.out.println(title);
           //System.out.println(path);
           //System.out.println(length);
           //System.out.println(comments);
           //System.out.println(category);
           //System.out.println(summary);
           //System.out.println(author);
           System.out.println(extraAuthor);
           //System.out.println(picturePath1);
           //System.out.println(picturePath2);
           
           
           
       }
           
           
             
        
	public void placeArticle(){
            
  
            
        
            Statement  stmt=null;
            
             Date date = new Date();  
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");  
            String strDate= formatter.format(date);  
      
            
            
            
            try{
          
                stmt = con.createStatement();
                stmt.executeUpdate("INSERT INTO `newspaper`.`article` (`state`, `title`, `path`, `length`, `magazine`, `newspaper`, `category`)"
                        + "VALUES ('applied', '"+title+"', '"+path+"', '"+length+"', '"+magazineNum+"', '"+newspaper+"', '"+category+"')");
                
                
                     
                stmt.executeUpdate("INSERT INTO `newspaper`.`submits` (`journalistSubEmail`, `submittedArticle`, `Date`) "
                        + "VALUES ('"+author+"', '"+path+"', '"+strDate+"');");
              
              
                
       
                
                if(keywords!=null){
                    
                       
                    stmt.executeUpdate("update article set keyWords='"+keywords+"' where path='"+path+"' ");
                    
               }
                
                
              if(summary!=null){
                        
                   
                    stmt.executeUpdate("update article set summary='"+summary+"' where path='"+path+"' ");
                    
               } 
              
                
                  
              if(extraAuthor!=null){
                  
                  stmt.executeUpdate("INSERT INTO `newspaper`.`submits` (`journalistSubEmail`, `submittedArticle`, `Date`) "
                        + "VALUES ('"+extraAuthor+"', '"+path+"', '"+strDate+"');");
                  

              }
              
              
              if(picturePath1!=null){
                  
                  
                  stmt.executeUpdate("insert into pictures values('"+picturePath1+"','"+path+"')");
                  
              }
              
              if(picturePath2!=null){
                  
                  stmt.executeUpdate("insert into pictures values('"+picturePath2+"','"+path+"')");
                  
              }
              
              

               JOptionPane.showMessageDialog(null, 
                                       "The article has been added succesfully", 
                                        "You just added an article", 
                                         JOptionPane.INFORMATION_MESSAGE);
                          
                
               System.out.println("The article has been added!");
                
            }
            catch(SQLException e){
                
                e.getMessage();
                JOptionPane.showMessageDialog(null, 
                                       "Sorry an error has been occured", 
                                        "Cannot add article", 
                                         JOptionPane.WARNING_MESSAGE);
                         
                System.out.println(e.getMessage());
            }
            finally{
            
                try{
                    
                if(stmt!=null) {   
                 stmt.close();
                }
                
                
                }
                catch(SQLException e)  {e.getMessage();}
                
            }
            
        
        }
}