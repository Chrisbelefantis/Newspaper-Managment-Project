

import customPopups.*;
import customPopups.updateCategory;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.CallableStatement;
import java.sql.ResultSetMetaData;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import classes.Article;
import java.io.File;
import javax.swing.ButtonModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;







public class journalistGui extends javax.swing.JFrame {

     private static Connection con=null;
     private  String journalistEmail;
     private  String journalistsNewspaper;
     
    
    public journalistGui(String email) {
        initComponents();   
        this.journalistEmail=email;
        databaseConnect();
        
        try{
            Statement st = con.createStatement();
            ResultSet se = st.executeQuery("select newspaperName from working where workerEmail='"+journalistEmail+"'");
            if(se.next()) this.journalistsNewspaper =se.getString("newspaperName");
            se.close();
            st.close();
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());;
        }
        
        
        initializeTab1();
        initializeTab2();   
        initializeTab3();
    }
      
    public void  initializeTab1(){ 
    
           
        
        DefaultComboBoxModel dm = new DefaultComboBoxModel(); 
           
        try{   
            Statement st  = con.createStatement();
            ResultSet se= st.executeQuery("select magazine.* from newspaper inner join magazine on newspaper.Name = magazine.newspaperName "
                    + "where newspaper.Name = \""+journalistsNewspaper+"\"");
            
            
            selectIssue.setModel(dm);
            
         
            while(se.next())
            {
                
                selectIssue.addItem("Νο."+ se.getString(2));
            }
            
            selectIssue.setSelectedIndex(-1);
            
            EditPanel.setVisible(false);
            st.close();
            se.close();
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());;
        }
       }
    
    public void initializeTab2(){
        
         DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
         model.setRowCount(0);
         String query = "{CALL journalistArticles(?)}";
         CallableStatement stmt=null;
         Statement stmt2 = null;
         ResultSet rs=null;
         ResultSet rs2=null;
         
         
         
         try{
             
             
             stmt = con.prepareCall(query);
             stmt.setString(1,journalistEmail);
             
              boolean isResultSet =stmt.execute();
            
            while(true){
                   
                   if(isResultSet){
                       rs =stmt.getResultSet();
                       
                       
                       
                       
                       if(rs.next()){
                           
                           
                           String state = rs.getString(1);
                           String title = rs.getString(2);
                           String magazineNum = rs.getString(3);
                           String position = rs.getString(4);
                           String categoryId = rs.getString(5);
                           String otherAuthor = rs.getString(6);
                           
                         
                           rs.close();
                           
                           
                           
                           if(position==null){
                               
                               position = "Not Placed Yet";
                           }
                           
                           
                           
                           stmt2 =  con.createStatement();  
                           rs2=stmt2.executeQuery("select categoryTitle from category where id ="+categoryId+"");
                           rs2.next(); 
                           String category = rs2.getString(1);
                           
                           
                           
                           
                           model.addRow(new Object[]{state,title,magazineNum,position
                                   ,category,otherAuthor});
                           


                       }
                       
                    
                   }
                   else{
                       if(stmt.getUpdateCount()==-1){
                           break;
                       }
                   }
                   
                   isResultSet = stmt.getMoreResults();
                   
               }
    
             }
    
         catch(SQLException e)
         {
            System.out.println(e.getMessage());;
         }finally{
                try{
                    
                if(rs!=null) {   
                 rs.close();
                }
                if(stmt!=null) {   
                 stmt.close();
                }
                 if(rs2!=null) {   
                 rs2.close();
                }
               if(stmt2!=null) {   
                 stmt2.close();
                }
                }
                catch(SQLException e)  {System.out.println(e.getMessage());;}
                
            }
         
     }  
        
        
        
        
    
    
    public void initializeTab3(){
        
         DefaultTableModel model2 = (DefaultTableModel) jTable2.getModel();
         model2.setRowCount(0);
         Statement stmt = null;
         ResultSet rs=null;
         
         
         
         try{
       
              stmt=con.createStatement();
              rs = stmt.executeQuery("select state,title" +
"                                    from article" +
"                                    inner join submits on article.path = submits.submittedArticle" +
"                                    where journalistSubEmail = '"+journalistEmail+"' and state <> 'accepted'" +
"                                     order by state,path asc");
                           
             
             while(rs.next())
             {
                model2.addRow(new Object[]{rs.getString(1),rs.getString(2)});
             } 

                
             }
   
  
         catch(SQLException e)
         {
            System.out.println(e.getMessage());;
         }finally{
                try{
                    
                if(rs!=null) {   
                 rs.close();
                }
                if(stmt!=null) {   
                 stmt.close();
                }
            
                }
                catch(SQLException e)  {System.out.println(e.getMessage());;}
                
            }
         
     }  
        
    
    
    public static void databaseConnect(){
       
       String Username = "root";
       String Password = "root";
       String CON_STRING = "jdbc:mysql://localhost:3306/newspaper?useSSL=false";
     
       try{
                con = DriverManager.getConnection(CON_STRING,Username,Password);
                System.out.println("I am connected to database!"); 
       
       }
       catch(SQLException e)
       {
          System.out.println(e.getMessage());
       }
       
       
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        picturesSelect = new javax.swing.ButtonGroup();
        jButton7 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jJournalistTabbedPanel = new javax.swing.JTabbedPane();
        newArticlePanel = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        selectIssue = new javax.swing.JComboBox<>();
        EditPanel = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        pathLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        selectLenght = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        linesAvailable = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectCategory = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryField = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        pathButton1 = new javax.swing.JButton();
        pathButton2 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        zeroPictures = new javax.swing.JRadioButton();
        onePicture = new javax.swing.JRadioButton();
        twoPictures = new javax.swing.JRadioButton();
        onePictureLabel = new javax.swing.JLabel();
        twoPicturesLabel = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        keywordsText = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        addAuthor = new javax.swing.JComboBox<>();
        updateArticlePanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton12 = new javax.swing.JButton();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        jButton7.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton7.setText("Update Title");

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton11.setText("Update Title");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Journalist");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jPanel3.setBackground(new java.awt.Color(166, 166, 167));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 32770));

        jButton6.setBackground(new java.awt.Color(23, 35, 38));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Update Article");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(23, 35, 38));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Show Articles");
        jButton8.setBorder(null);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(23, 35, 38));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Submit New Article");
        jButton9.setActionCommand("button1");
        jButton9.setBorder(null);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
                    .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(50, 50, 50)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(133, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(46, 76, 82));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Journalist");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(782, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 2, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(46, Short.MAX_VALUE))
        );

        jJournalistTabbedPanel.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jJournalistTabbedPanel.setPreferredSize(new java.awt.Dimension(753, 354));
        jJournalistTabbedPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jJournalistTabbedPanelMouseClicked(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Select newspapers issue: ");

        selectIssue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selectIssue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectIssue.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectIssueItemStateChanged(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton1.setText("Choose Path");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Add extra author:");

        selectLenght.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectLenght.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectLenghtActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Available pages:");

        linesAvailable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Add Keywords:");

        selectCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Select Category:");

        titleField.setBackground(new java.awt.Color(240, 240, 240));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel5.setText("Seperated with a comma");

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Write a quick summary:");

        summaryField.setColumns(20);
        summaryField.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        summaryField.setLineWrap(true);
        summaryField.setRows(5);
        summaryField.setWrapStyleWord(true);
        jScrollPane1.setViewportView(summaryField);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setText("Select number of Pictures:");

        pathButton1.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        pathButton1.setText("Choose Path");
        pathButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathButton1ActionPerformed(evt);
            }
        });

        pathButton2.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        pathButton2.setText("Choose Path");
        pathButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pathButton2ActionPerformed(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("Submit");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        picturesSelect.add(zeroPictures);
        zeroPictures.setText("0");
        zeroPictures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                zeroPicturesItemStateChanged(evt);
            }
        });

        picturesSelect.add(onePicture);
        onePicture.setText("1");
        onePicture.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                onePictureItemStateChanged(evt);
            }
        });

        picturesSelect.add(twoPictures);
        twoPictures.setText("2");
        twoPictures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                twoPicturesItemStateChanged(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel8.setText("Add Title:");

        keywordsText.setBackground(new java.awt.Color(240, 240, 240));

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel9.setText("Articles Lenght:");

        addAuthor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout EditPanelLayout = new javax.swing.GroupLayout(EditPanel);
        EditPanel.setLayout(EditPanelLayout);
        EditPanelLayout.setHorizontalGroup(
            EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator2)
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(addAuthor, 0, 147, Short.MAX_VALUE)
                                    .addComponent(titleField)))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(EditPanelLayout.createSequentialGroup()
                                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(EditPanelLayout.createSequentialGroup()
                                                .addGap(10, 10, 10)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(22, 22, 22))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditPanelLayout.createSequentialGroup()
                                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel5)
                                            .addComponent(jLabel3))
                                        .addGap(15, 15, 15)))
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(keywordsText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jButton1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGap(253, 253, 253)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(linesAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zeroPictures)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(onePicture)
                                .addGap(11, 11, 11)
                                .addComponent(twoPictures))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EditPanelLayout.createSequentialGroup()
                                        .addComponent(pathButton2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(twoPicturesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, EditPanelLayout.createSequentialGroup()
                                        .addComponent(pathButton1)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(onePictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(13, 13, 13)))
                .addContainerGap())
        );
        EditPanelLayout.setVerticalGroup(
            EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelLayout.createSequentialGroup()
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(linesAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGap(13, 13, 13)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(addAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(selectLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(zeroPictures, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(onePicture, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(twoPictures, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(15, 15, 15)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pathButton1)
                                    .addComponent(onePictureLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pathButton2)
                                    .addComponent(twoPicturesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(keywordsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(pathLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(20, 20, 20))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditPanelLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );

        javax.swing.GroupLayout newArticlePanelLayout = new javax.swing.GroupLayout(newArticlePanel);
        newArticlePanel.setLayout(newArticlePanelLayout);
        newArticlePanelLayout.setHorizontalGroup(
            newArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newArticlePanelLayout.createSequentialGroup()
                .addGroup(newArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(newArticlePanelLayout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(EditPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        newArticlePanelLayout.setVerticalGroup(
            newArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(newArticlePanelLayout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(newArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(selectIssue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jJournalistTabbedPanel.addTab("Submit New Article", newArticlePanel);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "State", "Title", "Newspaper issue", "Position", "Category", "Extra Author"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(jTable1);

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton12.setText("More Info");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Press the button bellow to see the comments of the rejected articles and more informations in general");

        javax.swing.GroupLayout updateArticlePanelLayout = new javax.swing.GroupLayout(updateArticlePanel);
        updateArticlePanel.setLayout(updateArticlePanelLayout);
        updateArticlePanelLayout.setHorizontalGroup(
            updateArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 744, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, updateArticlePanelLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton12)
                .addGap(323, 323, 323))
            .addGroup(updateArticlePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        updateArticlePanelLayout.setVerticalGroup(
            updateArticlePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(updateArticlePanelLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton12)
                .addContainerGap())
        );

        jJournalistTabbedPanel.addTab("Show Articles", updateArticlePanel);

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "State", "Title"
            }
        ));
        jScrollPane3.setViewportView(jTable2);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Choose the Update");

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Select the article you want to update");

        jButton5.setBackground(new java.awt.Color(23, 35, 38));
        jButton5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton5.setForeground(new java.awt.Color(255, 255, 255));
        jButton5.setText("Update Categotry");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton13.setBackground(new java.awt.Color(23, 35, 38));
        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Update Title");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(23, 35, 38));
        jButton10.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Update Lenght");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(23, 35, 38));
        jButton14.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Update Path");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton2.setText("Refresh");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("More Info");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(10, 10, 10)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 367, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(155, 155, 155)
                        .addComponent(jButton3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jButton13, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton14, javax.swing.GroupLayout.DEFAULT_SIZE, 255, Short.MAX_VALUE)
                            .addComponent(jSeparator3))
                        .addGap(33, 33, 33))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addGap(114, 114, 114))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(17, Short.MAX_VALUE))
        );

        jJournalistTabbedPanel.addTab("Update Article", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(jJournalistTabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jJournalistTabbedPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jJournalistTabbedPanel.getAccessibleContext().setAccessibleName("Submit New Article");

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jJournalistTabbedPanel.setSelectedIndex(2);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
       jJournalistTabbedPanel.setSelectedIndex(1);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jJournalistTabbedPanel.setSelectedIndex(0);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void selectIssueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectIssueItemStateChanged
                
                 zeroPictures.setSelected(true);
                 EditPanel.setVisible(true);
                JComboBox comboBox = (JComboBox) evt.getSource();

                Object item = evt.getItem();

               String num =  item.toString().substring(3,4);
              
                          
              linesAvailable.setText(Article.availableSpace(journalistsNewspaper,Integer.parseInt(num),con ));
              
               
        DefaultComboBoxModel dm1 = new DefaultComboBoxModel(); 
        DefaultComboBoxModel dm2 = new DefaultComboBoxModel(); 
        DefaultComboBoxModel dm3 = new DefaultComboBoxModel(); 
           
        try{   
            Statement st1  = con.createStatement();
            Statement st2  = con.createStatement();
            Statement st3  = con.createStatement();
            ResultSet se1= st1.executeQuery("select pages from magazine where newspaperName=\""+journalistsNewspaper+"\" and magazineNum="+num+"");
            ResultSet se2= st2.executeQuery("Select * from category");
            ResultSet se3= st3.executeQuery(" select journalistEmail from journalist "
                    + "inner join working on journalistEmail = workerEmail where newspaperName = \""+journalistsNewspaper+"\" and  journalistEmail <> '"+journalistEmail+"'");
           
            
            selectLenght.setModel(dm1);
            selectCategory.setModel(dm2);
            addAuthor.setModel(dm3);
         
            if(se1.next())
            {
                int pages = se1.getInt(1);
                for(int i=1; i<=pages; i++)
                {

                    selectLenght.addItem(Integer.toString(i));
                }
               
            }
            
            while(se2.next())
            {
                selectCategory.addItem(se2.getString(2));
            }
            
            addAuthor.addItem("none");
            
            while(se3.next())
            {
                addAuthor.addItem(se3.getString(1));
                
            }
            
            
            
            
            selectCategory.setSelectedIndex(-1);
            selectLenght.setSelectedIndex(-1);
            
  
            st1.close();
            st2.close();
            st3.close();
            se1.close();
            se2.close();
            se3.close();
            
        }
        catch(SQLException e)
        {
           System.out.println(e.getMessage());
        }
              
              
              
              
              
              
                   
    }//GEN-LAST:event_selectIssueItemStateChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        pathLabel.setText(filename);
        
        
        
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void pathButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathButton2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        twoPicturesLabel.setText(filename);
        
    }//GEN-LAST:event_pathButton2ActionPerformed

    private void zeroPicturesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_zeroPicturesItemStateChanged
        
        if(zeroPictures.isSelected())
        {
        
            pathButton1.setVisible(false);
            pathButton2.setVisible(false);
            onePictureLabel.setVisible(false);
            twoPicturesLabel.setVisible(false);
        }
        
        
        
    }//GEN-LAST:event_zeroPicturesItemStateChanged

    private void onePictureItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_onePictureItemStateChanged
           if(onePicture.isSelected())
        {
        
            pathButton1.setVisible(true);
            pathButton2.setVisible(false);
            onePictureLabel.setVisible(true);
            twoPicturesLabel.setVisible(false);
        }
        
    }//GEN-LAST:event_onePictureItemStateChanged

    private void twoPicturesItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_twoPicturesItemStateChanged
            if(twoPictures.isSelected())
        {
        
            pathButton1.setVisible(true);
            pathButton2.setVisible(true);
            onePictureLabel.setVisible(true);
            twoPicturesLabel.setVisible(true);
            
            
            
        }
    }//GEN-LAST:event_twoPicturesItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        
       
        String magazineNum;   
        String lenght; 
        String category;
        String keywords;
        String summary;
        String title;
        
        
        Object item =selectIssue.getSelectedItem();
        magazineNum = item.toString().substring(3,4);
       
        
        Article newArticle = new Article(journalistsNewspaper,Integer.parseInt(magazineNum),journalistEmail,con);
        
        
        
        
        
        if(titleField.getText().equals("")==false)
        {
            
            title = titleField.getText();
            newArticle.setTitle(title);           
            
            if(selectLenght.getSelectedIndex()!=-1){
          
                 
                item =selectLenght.getSelectedItem();
                lenght = item.toString();
                newArticle.setLenght(Integer.parseInt(lenght));         
          
                if(selectCategory.getSelectedIndex()!=-1){
              
                     item =selectCategory.getSelectedItem();
                      category=item.toString();
                      System.out.println(category);
                     newArticle.setCategory(category);
                   
                     
                     
                     if(pathLabel.getText().equals("")==false){
                     
                     
                         newArticle.setPath(pathLabel.getText());
                
                         newArticle.setKeywords(keywordsText.getText());

                         
                         if(summaryField.getText().equals("")==false)
                         {
                            newArticle.setSummary(summaryField.getText());
                         
                         }
                         
                         
                         if(addAuthor.getSelectedIndex()!=0){
                             
                               

                                Object extraAuhtor = addAuthor.getSelectedItem();

                                 newArticle.setExtraAuthor(extraAuhtor.toString());
                             
                             
                         }
                         
                         
                         
                         if(onePicture.isSelected())
                         {
                             
                             newArticle.setPicture(onePictureLabel.getText());
                         }
                         else if(twoPictures.isSelected()){
                             
                             newArticle.setPicture(onePictureLabel.getText(), twoPicturesLabel.getText());
                         }
                           
                         int availableLines =  Integer.parseInt(linesAvailable.getText());
                         int selectedLines = Integer.parseInt(lenght);
                         
                         
                         
                         
                         
                         
                         if(selectedLines<=availableLines  ){
                             
                             
                             
                             newArticle.placeArticle();
                             
                             initializeTab1();
                             
                             
                         }
                         else{
                             JOptionPane.showMessageDialog(null, 
                                       "Sorry your articles lenghs is more than the available lenght", 
                                        "Cannot add article", 
                                         JOptionPane.WARNING_MESSAGE);
                             
                         }
                         
                         
                         
                         
                         
                     }
                     else{
                         JOptionPane.showMessageDialog(null, 
                                       "Please select the articles path", 
                                        "Cannot add article", 
                                         JOptionPane.WARNING_MESSAGE);
                         
                     }
                     
                     
                     
                }
                else{
              
                    JOptionPane.showMessageDialog(null, 
                                       "Please select the articles category in pages", 
                                        "Cannot add article", 
                                         JOptionPane.WARNING_MESSAGE);
              
              
                  }
             }
             else{
         
                 JOptionPane.showMessageDialog(null, 
                                   "Please select the articles length in pages", 
                                   "Cannot add article", 
                                   JOptionPane.WARNING_MESSAGE);
              }
      
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please add Title to your article", 
                                   "Cannot add article", 
                                   JOptionPane.WARNING_MESSAGE);
            
        }
       
      
      
      
      initializeTab2();
        
    }//GEN-LAST:event_jButton4ActionPerformed

    private void selectLenghtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectLenghtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_selectLenghtActionPerformed

    private void pathButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathButton1ActionPerformed
         JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        onePictureLabel.setText(filename);
        
        
        
    }//GEN-LAST:event_pathButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        
        int row = jTable2.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable2.getModel().getValueAt(row, 1).toString();
            updateCategory updateGui = new updateCategory(selectedTitle,con);
            updateGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
                                        

        
        
        
        
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        
         int row = jTable2.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable2.getModel().getValueAt(row, 1).toString();
            updateLengh lenghGui = new updateLengh(selectedTitle,con);
            lenghGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
       
        
            int row = jTable2.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable2.getModel().getValueAt(row, 1).toString();
            updatePath pathGui = new updatePath(selectedTitle,con);
            pathGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        
        
        
        int row = jTable2.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable2.getModel().getValueAt(row, 1).toString();
            updateTitle titleGui = new updateTitle(selectedTitle,con);
            titleGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        initializeTab2();
        initializeTab3();
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jJournalistTabbedPanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jJournalistTabbedPanelMouseClicked
       initializeTab3();
    }//GEN-LAST:event_jJournalistTabbedPanelMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        int row = jTable2.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable2.getModel().getValueAt(row, 1).toString();
            articleDetails articleGui = new articleDetails(selectedTitle,con);
            articleGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
           int row = jTable1.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable1.getModel().getValueAt(row, 1).toString();
            articleDetails articleGui = new articleDetails(selectedTitle,con);
            articleGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot update", 
                                   JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_jButton12ActionPerformed

    
    public static void main(String args[]) {
        
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(journalistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(journalistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(journalistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(journalistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

    

       
       
        
    }
    
            
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EditPanel;
    private javax.swing.JComboBox<String> addAuthor;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JTabbedPane jJournalistTabbedPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField keywordsText;
    private javax.swing.JLabel linesAvailable;
    private javax.swing.JPanel newArticlePanel;
    private javax.swing.JRadioButton onePicture;
    private javax.swing.JLabel onePictureLabel;
    private javax.swing.JButton pathButton1;
    private javax.swing.JButton pathButton2;
    private javax.swing.JLabel pathLabel;
    private javax.swing.ButtonGroup picturesSelect;
    private javax.swing.JComboBox<String> selectCategory;
    private javax.swing.JComboBox<String> selectIssue;
    private javax.swing.JComboBox<String> selectLenght;
    private javax.swing.JTextArea summaryField;
    private javax.swing.JTextField titleField;
    private javax.swing.JRadioButton twoPictures;
    private javax.swing.JLabel twoPicturesLabel;
    private javax.swing.JPanel updateArticlePanel;
    private javax.swing.JRadioButton zeroPictures;
    // End of variables declaration//GEN-END:variables
}
