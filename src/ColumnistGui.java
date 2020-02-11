import customPopups.*;
import java.io.File;
import classes.Article;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.CallableStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.table.DefaultTableModel;


public class ColumnistGui extends javax.swing.JFrame {

    
    
    private String email;
    private static Connection con=null;
    private  String ColumnistNewspaper;
    
    public ColumnistGui() {
        initComponents();
    }

     public ColumnistGui(String email) {
        initComponents();
        this.email = email;
        databaseConnect();
        
        Statement st = null;
        ResultSet se = null;
        try{
            st = con.createStatement();
            se = st.executeQuery("select newspaperName from working where workerEmail='"+email+"'");
            if(se.next()) this.ColumnistNewspaper =se.getString("newspaperName");
            se.close();
            st.close();
            
        }
        catch(SQLException e){
            System.out.println(e.getMessage());;
        }finally{
            try{
                    
                if(st!=null) {   
                 st.close();
                }
                if(se!=null) {   
                 se.close();
                }
             }
                catch(SQLException e)  {System.out.println(e.getMessage());;}
                
            }
  
          
         initializeTab1();
         initializeTab2();
         initializeTab3();
         initializeTab4();
         
        
    }
     
     
     
     public void initializeTab1(){
         
         DefaultTableModel model= (DefaultTableModel) jTable1.getModel(); 
         model.setRowCount(0);
         Statement stmt = null;
         ResultSet rs=null;
         ResultSet rs2=null;
         
         
         try{
       
              stmt=con.createStatement();
              rs = stmt.executeQuery("select state,title,magazine,length,category,journalistSubEmail from article inner join submits on path = submittedArticle "
                      + "where newspaper = '"+ColumnistNewspaper+"' order by state,title");
              
              
           
       
              
             boolean hasNext = rs.next();     
             
             while(hasNext)
             {
                 
                 
                           
                           
                           String state = rs.getString(1);
                           String title = rs.getString(2);
                           String author = rs.getString(6);
                      
                           
                         
                            if(rs.next()){

                                
                                    if(rs.getString(2).equals(title)){
                                        
                                        String secondAuthor = rs.getString(6);
                                        model.addRow(new Object[]{state,title,author,secondAuthor});
                                        hasNext = rs.next();
                                    
                                    }
                                    else{
                                        
                                        model.addRow(new Object[]{state,title,author,"none"});
                                        

                                    }
                                    
                               }
                            
                            
                            
                            
                            else{
                                
                                model.addRow(new Object[]{state,title,author,"none"});
                                break;
                                
                            }


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
                 stmt.close();
                }
                }
                catch(SQLException e)  {System.out.println(e.getMessage());;}
                
            }
         
         
         
         
     }

    
       public void initializeTab2(){
           
           
          DefaultComboBoxModel dm = new DefaultComboBoxModel(); 
          Statement st = null;
          ResultSet se = null;
           
        try{   
            st  = con.createStatement();
            se= st.executeQuery("select magazine.* from newspaper inner join magazine on newspaper.Name = magazine.newspaperName "
                    + "where newspaper.Name = \""+ColumnistNewspaper+"\"");
            
            
            selectIssue.setModel(dm);
            
         
            while(se.next())
            {
                
                selectIssue.addItem("Νο."+ se.getString(2));
            }
            
            selectIssue.setSelectedIndex(-1);
            
           
      
            
            
            editPanel.setVisible(false);
        
          
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());;
        }finally{
                    try{
                    
                if(st!=null) {   
                 st.close();
                }
                if(se!=null) {   
                 se.close();
                }
             }catch(SQLException e)  {System.out.println(e.getMessage());;}
        }
    
    }
    
           
  public static boolean allDifferent( String[]a){
        for(int i = 0; i < a.length - 1; i++){
         for (int j = i + 1; j < a.length; j++){
             if (a[i].equals(a[j]))
                 return false;
         }
    }
    return true;
  }    
           
       
    public  void initializeTab3(){
        
        DefaultComboBoxModel dm = new DefaultComboBoxModel(); 
           
        try{   
            Statement st  = con.createStatement();
            ResultSet se= st.executeQuery("select magazine.* from newspaper inner join magazine on newspaper.Name = magazine.newspaperName "
                    + "where newspaper.Name = \""+ColumnistNewspaper+"\"");
            
            
            selectIssue1.setModel(dm);
            
         
            while(se.next())
            {
                
                selectIssue1.addItem("Νο."+ se.getString(2));
            }
            
            selectIssue1.setSelectedIndex(-1);
            
            EditPanel.setVisible(false);
            st.close();
            se.close();
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());;
        }
       
        
        
        
        
        
        
        
    }
           
           
    public void initializeTab4(){
        
         
          DefaultComboBoxModel dm = new DefaultComboBoxModel(); 
          Statement st = null;
          ResultSet se = null;
           
        try{   
            st  = con.createStatement();
            se= st.executeQuery("select categoryTitle from category");
            
            
            subcategoryField.setModel(dm);
            
         
            subcategoryField.addItem("none");
            
            
            while(se.next())
            {
                
                subcategoryField.addItem(se.getString(1));
            }
            
            
            
   
        
          
            
        }
        catch(SQLException e)
        {
            System.out.println(e.getMessage());;
        }finally{
                    try{
                    
                if(st!=null) {   
                 st.close();
                }
                if(se!=null) {   
                 se.close();
                }
             }catch(SQLException e)  {System.out.println(e.getMessage());;}
        }
           
 }
     
     
    
       public static void databaseConnect(){
       
       String Username = "root";
       String Password = "root";
       String CON_STRING = "jdbc:mysql://localhost:3306/newspaper?useSSL=false";
       boolean canLog = false;
       
       try{
                con =DriverManager.getConnection(CON_STRING,Username,Password);
                System.out.println("I am connected to database!!!"); 
       
       }
       catch(SQLException e)
       {
           System.out.println(e.getMessage());
       }    
    }
       
       
       
    /**
     * 
     * 
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        selectIssue = new javax.swing.JComboBox<>();
        jSeparator2 = new javax.swing.JSeparator();
        editPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        selectIssue1 = new javax.swing.JComboBox<>();
        EditPanel = new javax.swing.JPanel();
        jButton4 = new javax.swing.JButton();
        pathLabel = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        selectLenght = new javax.swing.JComboBox<>();
        jSeparator3 = new javax.swing.JSeparator();
        jLabel12 = new javax.swing.JLabel();
        linesAvailable = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        selectCategory = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        titleField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        summaryField = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();
        pathButton1 = new javax.swing.JButton();
        pathButton2 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        zeroPictures = new javax.swing.JRadioButton();
        onePicture = new javax.swing.JRadioButton();
        twoPictures = new javax.swing.JRadioButton();
        onePictureLabel = new javax.swing.JLabel();
        twoPicturesLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        keywordsText = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        addAuthor = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        categoryField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        subcategoryField = new javax.swing.JComboBox<>();
        saveButtom = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        descriptionField = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Editor in Chief");
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(46, 76, 82));

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Editor in Chief");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 316, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(634, Short.MAX_VALUE))
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

        jPanel3.setBackground(new java.awt.Color(166, 166, 167));
        jPanel3.setMaximumSize(new java.awt.Dimension(32767, 32770));

        jButton6.setBackground(new java.awt.Color(23, 35, 38));
        jButton6.setForeground(new java.awt.Color(255, 255, 255));
        jButton6.setText("Submit New Article");
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton8.setBackground(new java.awt.Color(23, 35, 38));
        jButton8.setForeground(new java.awt.Color(255, 255, 255));
        jButton8.setText("Set Articles Position");
        jButton8.setBorder(null);
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setBackground(new java.awt.Color(23, 35, 38));
        jButton9.setForeground(new java.awt.Color(255, 255, 255));
        jButton9.setText("Article Manager");
        jButton9.setActionCommand("button1");
        jButton9.setBorder(null);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        jButton10.setBackground(new java.awt.Color(23, 35, 38));
        jButton10.setForeground(new java.awt.Color(255, 255, 255));
        jButton10.setText("Add New Category");
        jButton10.setBorder(null);
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
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
                    .addComponent(jButton10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(jButton9, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton8, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32)
                .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton13.setBackground(new java.awt.Color(23, 35, 38));
        jButton13.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton13.setForeground(new java.awt.Color(255, 255, 255));
        jButton13.setText("Reject Article with Comments");
        jButton13.setToolTipText("");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });

        jButton14.setBackground(new java.awt.Color(23, 35, 38));
        jButton14.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jButton14.setForeground(new java.awt.Color(255, 255, 255));
        jButton14.setText("Reject Article with No Comments");
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });

        jButton15.setBackground(new java.awt.Color(23, 35, 38));
        jButton15.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jButton15.setForeground(new java.awt.Color(255, 255, 255));
        jButton15.setText("Accept Article");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });

        jButton1.setText("Refresh");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton3.setText("More Info");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "State", "Title", "Author", "Second Author"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton13, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton14, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 222, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(168, 168, 168)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(74, 74, 74))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 206, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1))
                .addGap(22, 22, 22))
        );

        jTabbedPane1.addTab("Article Manager", jPanel2);

        jLabel13.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel13.setText("Select newspapers issue: ");

        selectIssue.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selectIssue.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectIssue.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectIssueItemStateChanged(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Current Postion", "Article's Title", "Select New Position"
            }
        ));
        jScrollPane2.setViewportView(jTable2);

        jButton2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Make sure that you set the position for every article and the positions are distinct");

        javax.swing.GroupLayout editPanelLayout = new javax.swing.GroupLayout(editPanel);
        editPanel.setLayout(editPanelLayout);
        editPanelLayout.setHorizontalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 624, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(editPanelLayout.createSequentialGroup()
                        .addGap(307, 307, 307)
                        .addComponent(jButton2)))
                .addContainerGap(54, Short.MAX_VALUE))
        );
        editPanelLayout.setVerticalGroup(
            editPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(editPanelLayout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addGap(47, 47, 47))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addGap(142, 142, 142)
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectIssue, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 182, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(editPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(selectIssue))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(editPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(235, 235, 235))
        );

        jTabbedPane1.addTab("Set Articles Position", jPanel4);

        jLabel14.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel14.setText("Select newspapers issue: ");

        selectIssue1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        selectIssue1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectIssue1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                selectIssue1ItemStateChanged(evt);
            }
        });

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jButton4.setText("Choose Path");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel3.setText("Add extra author:");

        selectLenght.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        selectLenght.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectLenghtActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setText("Available pages:");

        linesAvailable.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel4.setText("Add Keywords:");

        selectCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setText("Select Category:");

        titleField.setBackground(new java.awt.Color(240, 240, 240));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        jLabel6.setText("Seperated with a comma");

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Write a quick summary:");

        summaryField.setColumns(20);
        summaryField.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        summaryField.setLineWrap(true);
        summaryField.setRows(5);
        summaryField.setWrapStyleWord(true);
        jScrollPane3.setViewportView(summaryField);

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setText("Select number of Pictures:");

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

        jButton5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton5.setText("Submit");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        zeroPictures.setText("0");
        zeroPictures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                zeroPicturesItemStateChanged(evt);
            }
        });

        onePicture.setText("1");
        onePicture.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                onePictureItemStateChanged(evt);
            }
        });

        twoPictures.setText("2");
        twoPictures.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                twoPicturesItemStateChanged(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel9.setText("Add Title:");

        keywordsText.setBackground(new java.awt.Color(240, 240, 240));

        jLabel10.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel10.setText("Articles Lenght:");

        addAuthor.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout EditPanelLayout = new javax.swing.GroupLayout(EditPanel);
        EditPanel.setLayout(EditPanelLayout);
        EditPanelLayout.setHorizontalGroup(
            EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(EditPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator3)
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING))
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
                                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(22, 22, 22))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, EditPanelLayout.createSequentialGroup()
                                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel4))
                                        .addGap(15, 15, 15)))
                                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(keywordsText, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(selectLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGap(11, 11, 11)
                                .addComponent(jButton4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(pathLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addGap(253, 253, 253)
                                .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(linesAvailable, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(3, 3, 3)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(EditPanelLayout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(zeroPictures)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(onePicture)
                                .addGap(11, 11, 11)
                                .addComponent(twoPictures))
                            .addComponent(jScrollPane3)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(13, 13, 13)
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(titleField, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(addAuthor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(selectLenght, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(EditPanelLayout.createSequentialGroup()
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(selectCategory, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(keywordsText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
                        .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(EditPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton4, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(pathLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(185, 185, 185)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(selectIssue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(EditPanel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(1, 1, 1)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                    .addComponent(selectIssue1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EditPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Submit New Article", jPanel5);

        jLabel15.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel15.setText("Category name:");

        categoryField.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N

        jLabel16.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel16.setText("Subcateogory of:");

        subcategoryField.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        subcategoryField.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        saveButtom.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        saveButtom.setText("Save");
        saveButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtomActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel17.setText("Description:");

        descriptionField.setColumns(20);
        descriptionField.setFont(new java.awt.Font("Monospaced", 0, 24)); // NOI18N
        descriptionField.setLineWrap(true);
        descriptionField.setRows(5);
        descriptionField.setWrapStyleWord(true);
        jScrollPane4.setViewportView(descriptionField);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel15)
                            .addComponent(jLabel16))
                        .addGap(61, 61, 61)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(subcategoryField, 0, 202, Short.MAX_VALUE)
                            .addComponent(categoryField)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(285, 285, 285)
                        .addComponent(saveButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 431, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(275, 275, 275)
                        .addComponent(jLabel17)))
                .addContainerGap(163, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(categoryField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(subcategoryField))
                .addGap(22, 22, 22)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(saveButtom, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(19, 19, 19))
        );

        jTabbedPane1.addTab("Add New Category", jPanel6);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 378, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        jTabbedPane1.setSelectedIndex(2);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        jTabbedPane1.setSelectedIndex(1);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        jTabbedPane1.setSelectedIndex(0);
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        jTabbedPane1.setSelectedIndex(3);
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed

           int row = jTable1.getSelectedRow();
        
        if(row!=-1){
            
          String selectedState = jTable1.getModel().getValueAt(row, 0).toString();
          
            if(!(selectedState.equals("accepted"))){
            
                String selectedTitle = jTable1.getModel().getValueAt(row, 1).toString();
                commentsGui cGui = new commentsGui(selectedTitle,con);
                cGui.setVisible(true);
            }
            else{
                
                JOptionPane.showMessageDialog(null, 
                                   "You cant reject an accepted article", 
                                   "Cannot reject", 
                                   JOptionPane.WARNING_MESSAGE);
            }
            
            
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot delete", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
     
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        
          int row = jTable1.getSelectedRow();
           
        
        if(row!=-1){
            
            String selectedState = jTable1.getModel().getValueAt(row, 0).toString();
            
            if(!(selectedState.equals("accepted"))){
                String selectedTitle = jTable1.getModel().getValueAt(row, 1).toString();
                Statement stmt = null;

                try{
                    stmt = con.createStatement();

                    stmt.executeUpdate("update article set state = 'rejected' where title = '"+selectedTitle+"' ");

                     JOptionPane.showMessageDialog(null, 
                                       "The article has been deleted", 
                                       "Article Rejected", 
                                       JOptionPane.INFORMATION_MESSAGE);

                     initializeTab1();


                }
                catch(SQLException e){
                    System.out.println(e.getMessage());

                }
                finally{

                    try{
                        if(stmt!=null){

                            stmt.close();
                        }

                    }catch(SQLException e){

                        System.out.println(e.getMessage());

                    }

                }
            }
            else{
                
                JOptionPane.showMessageDialog(null, 
                                   "You cant reject an accepted article", 
                                   "Cannot reject", 
                                   JOptionPane.WARNING_MESSAGE);
                
                
            }
            
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot reject", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
      
           int row = jTable1.getSelectedRow();
 

         
            if(row!=-1 ){
                   
                String selectedTitle = jTable1.getModel().getValueAt(row, 1).toString();
                String selectedState =  jTable1.getModel().getValueAt(row, 0).toString();
                Statement stmt = null;
                Statement stmt2 = null;
                ResultSet rs = null;
                
                if(selectedState.equals("accepted")==false){
                    try{
                        stmt = con.createStatement();
                        stmt.executeUpdate("update article set state = 'accepted' where title = '"+selectedTitle+"' ");
                        
                        stmt2 = con.createStatement();
                        rs = stmt2.executeQuery("select path from article where title ='"+selectedTitle+"' ");
                        
                        if(rs.next()){
                            
                            
                            
                            Date date = new Date();  
                            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-M-dd");  
                            String strDate= formatter.format(date);  
                            
                            String selectedPath = rs.getString("path");
                            stmt.executeUpdate("insert into approves values('"+email+"','"+selectedPath+"','"+strDate+"') ");
                        }
                        
                        
                         JOptionPane.showMessageDialog(null, 
                                           "The article has accepted", 
                                           "Article accepted", 
                                           JOptionPane.INFORMATION_MESSAGE);

                         initializeTab1();


                    }
                    catch(SQLException e){
                        
                        String error = e.getMessage();
                        if(error.equals("Column count doesn't match value count at row 1"))
                        {   
                             JOptionPane.showMessageDialog(null, 
                                           "There is not enough space in the magazine", 
                                           "You cant accepted", 
                                           JOptionPane.WARNING_MESSAGE);
                            
                        }
                        else { System.out.println(error);}
                    }
                    finally{

                        try{
                            if(stmt!=null){

                                stmt.close();
                            }
                            if(stmt2!=null){

                                stmt2.close();
                            }
                            if(rs!=null){

                                rs.close();
                            }

                        }catch(SQLException e){

                            System.out.println(e.getMessage());

                        }

                    }

                }
                else if(selectedState.equals("accepted")==true){
                    JOptionPane.showMessageDialog(null, 
                                           "You cannot perform any action in accepted articles", 
                                           "Error", 
                                           JOptionPane.WARNING_MESSAGE);
                
                    
                }
        }

       else{

            JOptionPane.showMessageDialog(null, 
                                "Please select one article", 
                                "Cannot accept", 
                                JOptionPane.WARNING_MESSAGE);
            }
        
       
        
    }//GEN-LAST:event_jButton15ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        initializeTab1();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
         
         int row = jTable1.getSelectedRow();
        
        if(row!=-1){
            
            String selectedTitle = jTable1.getModel().getValueAt(row, 1).toString();
            articleDetails articleGui = new articleDetails(selectedTitle,con);
            articleGui.setVisible(true);
        }
        else{
            
            JOptionPane.showMessageDialog(null, 
                                   "Please select one article", 
                                   "Cannot reject", 
                                   JOptionPane.WARNING_MESSAGE);
        }
        
        
        
        
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void selectIssueItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectIssueItemStateChanged

      
        //EditPanel.setVisible(true);
       editPanel.setVisible(true);
       
        JComboBox comboBox = (JComboBox) evt.getSource();

        Object item = evt.getItem();
        String num =  item.toString().substring(3);
        
        
        DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
        model.setRowCount(0);
        
        Statement st = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        
        
        try{
            
            st = con.createStatement();
            rs2 = st.executeQuery("select count(*) from article "
                    + "where newspaper = \""+ColumnistNewspaper+"\" and magazine = "+num+" and state = \"accepted\";");
                 
          
            
            rs2.next(); 
            int count = Integer.parseInt(rs2.getString(1));

            String positions[] = new String[count];

            for(int i=0; i<count; i++){

                positions[i] = Integer.toString(i+1);

            }
            
            
            JComboBox position = new JComboBox(positions);
            
            
             rs = st.executeQuery("select position,Title from article "
                    + "where newspaper = \""+ColumnistNewspaper+"\" and magazine = "+num+" and state = \"accepted\"  order by position asc;");
            
            
            while(rs.next()){
                
                model.addRow(new Object[]{rs.getString(1),rs.getString(2),"Click to edit"} );
                
            }
            
            jTable2.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(position));
            
            
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            
        }finally{        
            try{
                
                if(st!=null){
                    st.close();
                }
                if(rs!=null){
                    
                    rs.close();
                }
                if(rs2!=null){
                    
                    rs2.close();
                }
                
            
            }catch(SQLException e)    { System.out.println(e.getMessage());  }
      }

            
          
        
        
        
        
    }//GEN-LAST:event_selectIssueItemStateChanged

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        
        int count = jTable2.getRowCount();
        boolean notSelected = false;
        String[] positions = new String[count];
        
        
        for(int i = 0 ; i<count; i++){
            
            
            positions[i] = jTable2.getValueAt(i,2).toString();
            
            if(positions[i].equals("Click to edit")){
                notSelected = true;
            }
            
        }
 
        
        if(allDifferent(positions) && !notSelected){
            
           
            Statement st = null;
            try{
            
                
                st = con.createStatement();
                
                for(int i=0 ; i<count; i++){
                    
                    String title = jTable2.getValueAt(i, 1).toString();
                    
                    st.executeUpdate("update article set position = "+positions[i]+" where title = '"+title+"'");
                    
                    
                }
                
                
             


                JOptionPane.showMessageDialog(null, 
                                           "Action performed", 
                                            "Your changes have been saved", 
                                             JOptionPane.INFORMATION_MESSAGE);

                st.close();
            }catch(SQLException e){
                
                System.out.println(e.getMessage());
            }
            
        }else if(notSelected){
            
             JOptionPane.showMessageDialog(null, 
                                       "Please select the position for every article", 
                                        "Cannot perform this action", 
                                         JOptionPane.WARNING_MESSAGE);
            
        }else{
            
             JOptionPane.showMessageDialog(null, 
                                       "Two or more articles have the same position", 
                                       "Cannot perform this action", 
                                         JOptionPane.WARNING_MESSAGE);
            
        }
        
        
        
        
        
    }//GEN-LAST:event_jButton2ActionPerformed

    private void selectIssue1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_selectIssue1ItemStateChanged

        zeroPictures.setSelected(true);
        EditPanel.setVisible(true);
        JComboBox comboBox = (JComboBox) evt.getSource();

        Object item = evt.getItem();

        String num =  item.toString().substring(3);

        linesAvailable.setText(Article.availableSpace(ColumnistNewspaper,Integer.parseInt(num),con ));

        DefaultComboBoxModel dm1 = new DefaultComboBoxModel();
        DefaultComboBoxModel dm2 = new DefaultComboBoxModel();
        DefaultComboBoxModel dm3 = new DefaultComboBoxModel();

        try{
            Statement st1  = con.createStatement();
            Statement st2  = con.createStatement();
            Statement st3  = con.createStatement();
            ResultSet se1= st1.executeQuery("select pages from magazine where newspaperName=\""+ColumnistNewspaper+"\" and magazineNum="+num+"");
            ResultSet se2= st2.executeQuery("Select * from category");
            ResultSet se3= st3.executeQuery(" select journalistEmail from journalist "
                + "inner join working on journalistEmail = workerEmail where newspaperName = \""+ColumnistNewspaper+"\" and  journalistEmail <> '"+email+"'");

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

    }//GEN-LAST:event_selectIssue1ItemStateChanged

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        pathLabel.setText(filename);

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

    private void pathButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pathButton2ActionPerformed
        JFileChooser chooser = new JFileChooser();
        chooser.showOpenDialog(null);
        File f = chooser.getSelectedFile();
        String filename = f.getAbsolutePath();
        twoPicturesLabel.setText(filename);

    }//GEN-LAST:event_pathButton2ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed

        String magazineNum;
        String lenght;
        String category;
        String keywords;
        String summary;
        String title;

        Object item =selectIssue1.getSelectedItem();
        magazineNum = item.toString().substring(3,4);

        Article newArticle = new Article(ColumnistNewspaper,Integer.parseInt(magazineNum),email,con);

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

                            initializeTab3();
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

       

    }//GEN-LAST:event_jButton5ActionPerformed

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

    private void saveButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtomActionPerformed
        
       
        Statement st = null;
        ResultSet rs = null;
        Statement st2 = null;
        String subcategoryOf = null;
        String subcategoryId = null;
        String description = null;
        String newCategory;
        
        
        
        
        
        
        
        
        try{
            
            
            
            newCategory = categoryField.getText();

       
            if(subcategoryField.getSelectedIndex()!=0){
                


                Object item =subcategoryField.getSelectedItem();
                subcategoryOf = item.toString();
                
                
                st = con.createStatement();
                rs = st.executeQuery("select id from category where categoryTitle='"+subcategoryOf+"';");
                
                if(rs.next()){
                    
                    subcategoryId = rs.getString("id");  
                  
                    
                }
            }
            
            if(!descriptionField.getText().equals("")){
                
                
                description = descriptionField.getText();
                
            }
            
            
            st2 = con.createStatement();
            
            if(!categoryField.getText().equals("")){
                
                if(description!= null && subcategoryId!=null){
                    
               
                    st2.executeUpdate("insert into category values(NULL,'"+newCategory+"','"+description+"',"
                        + "'"+subcategoryId+"')");
                }
                else if(description== null && subcategoryId!=null){
                        
                   
                        st2.executeUpdate("insert into category values(NULL,'"+newCategory+"',NULL,"
                        + "'"+subcategoryId+"')");
                }
                else if(description!= null && subcategoryId==null){
                    
                         st2.executeUpdate("insert into category values(NULL,'"+newCategory+"','"+description+"', NULL) ");
                       
                }else{
                  
                     st2.executeUpdate("insert into category values(NULL,'"+newCategory+"',NULL, NULL) ");
                }
                
                JOptionPane.showMessageDialog(null, 
                                   "You just added a new category", 
                                   "New category added", 
                                   JOptionPane.INFORMATION_MESSAGE);
                
                initializeTab4();
                
            }else{
                
                JOptionPane.showMessageDialog(null, 
                                   "Please insert the name of the new category", 
                                   "Cannot add category", 
                                   JOptionPane.WARNING_MESSAGE);
                
            }
            
            

            
        
        }catch(SQLException e){
            
            System.out.println(e.getMessage());
            
            
        }finally{
            try{
                
                if(st!=null){
                    
                    st.close();
                }
                if(rs!=null){
                    rs.close();
                }
                  if(st2!=null){
                    st2.close();
                }
                
                
            }catch(SQLException e) { System.out.println(e.getMessage()); }
            
            
        }
        
        
        
        
        
        
    }//GEN-LAST:event_saveButtomActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
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
            java.util.logging.Logger.getLogger(ColumnistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ColumnistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ColumnistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ColumnistGui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ColumnistGui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel EditPanel;
    private javax.swing.JComboBox<String> addAuthor;
    private javax.swing.JTextField categoryField;
    private javax.swing.JTextArea descriptionField;
    private javax.swing.JPanel editPanel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
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
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField keywordsText;
    private javax.swing.JLabel linesAvailable;
    private javax.swing.JRadioButton onePicture;
    private javax.swing.JLabel onePictureLabel;
    private javax.swing.JButton pathButton1;
    private javax.swing.JButton pathButton2;
    private javax.swing.JLabel pathLabel;
    private javax.swing.JButton saveButtom;
    private javax.swing.JComboBox<String> selectCategory;
    private javax.swing.JComboBox<String> selectIssue;
    private javax.swing.JComboBox<String> selectIssue1;
    private javax.swing.JComboBox<String> selectLenght;
    private javax.swing.JComboBox<String> subcategoryField;
    private javax.swing.JTextArea summaryField;
    private javax.swing.JTextField titleField;
    private javax.swing.JRadioButton twoPictures;
    private javax.swing.JLabel twoPicturesLabel;
    private javax.swing.JRadioButton zeroPictures;
    // End of variables declaration//GEN-END:variables
}
