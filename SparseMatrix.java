package matrixcomputation;

import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class SparseMatrix {

   //implement all pre-defined methods below and add your own methods as needed.
    
   private  Node[] rowHeads;
   private  Node[] colHeads;
   private	int size;

   public SparseMatrix(Node[] r, Node[] c) {
      rowHeads = r;
      colHeads = c;
   }
   
   //Constructor to create Matrix with size value
   public SparseMatrix(Node[] r, Node[] c, int size)
   {
      rowHeads = r;
      colHeads = c;
      this.size = size;
   }
   
   public static SparseMatrix initializeByInput(File file) 
   {
      SparseMatrix result = null;
      try
      {
         BufferedReader br = new BufferedReader(new FileReader(file));  
         String line = null;  
         ArrayList<String> mat = new ArrayList<String>();
         
         while ((line = br.readLine()) != null)  
         {  
            mat.add(line);
         }
         
         int size = Integer.parseInt(mat.get(0)); //Size of Matrix
         
         Node[] nodesD = new Node[size]; 
         Node[] rowD = new Node[size];
         Node[] colD = new Node[size];
         
         for (int i = 1; i < (mat.size()); i++)
         {
            line = mat.get(i);
            String[] temp = line.split("\\s+");
            int r = Integer.parseInt(temp[0]);
            int c = Integer.parseInt(temp[1]);
            int v = Integer.parseInt(temp[2]);
            Node t = new Node(null, null, v, r, c);
            nodesD[i-1] = t;
         }
         for (int i = 0; i < size; i++)
         {
            rowD[i] = new Node(null, null, 0, i+1, 0);
            colD[i] = new Node(null, null, 0, 0, i+1);
         }  
         int n = size;
         for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      br.close();
      //System.out.println(rowD[0].rowLink.rowLink);
      result = new SparseMatrix(rowD, colD, n);
      
      }       
      catch (Exception ex)
      {
         System.out.println(ex);
      }
   return result;
   }
   
   //parameter n --> given matrix size n
   public static SparseMatrix[] initializeByFormula(int n) {
      SparseMatrix[] result = null;
      result = new SparseMatrix[3];
      
      Node[] nodesB = new Node[n]; 
      Node[] nodesC = new Node[n-1]; // Last row is never filled
      Node[] nodesD;
      Node[] rowHead = new Node[n];
      Node[] colHead = new Node[n];
      Node[] rowB = new Node[n];
      Node[] colB = new Node[n];
      Node[] rowC = new Node[n];
      Node[] colC = new Node[n];
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      
      for (int i = 0; i < n; i++)
         {
            rowB[i] = new Node(null, null, 0, i+1, 0);
            colB[i] = new Node(null, null, 0, 0, i+1);
         } 
      //Matrix B
      
      for (int i = 0; i < n; i++)
      {
         for (int j = 0; j < n; j++)
         {
            if (i == j)
            {
               Node nd = new Node(rowB[i], colB[j], i+1, i+1, j+1);
               nodesB[i] = nd;
            }
         }
      }
      
      for (int i = 0; i < n; i++)
      {
         rowB[i] = new Node(nodesB[i], rowB[i].colLink, rowB[i].value, rowB[i].row, rowB[i].col);
         colB[i] = new Node(colB[i].rowLink, nodesB[i], colB[i].value, colB[i].row, colB[i].col);
      }
      //System.out.println(rowB[1].rowLink.rowLink.rowLink);
      result[0] = new SparseMatrix(rowB, colB, n);
      
      //Matrix C
      for (int i = 0; i < n; i++)
      {
         rowC[i] = new Node(null, null, 0, i+1, 0);
         colC[i] = new Node(null, null, 0, 0, i+1);
      } 
      for (int i = 1; i < n+1; i++)
      {
         for (int j = 1; j < n+1; j++)
         {
            if ( i == (j+1)%n)
            {
               Node nd = new Node(null, null, (-2*j-i), i, j);
               nodesC[i-1] = nd;
            }   
         }
      }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesC.length; j++)
            {
               if (nodesC[j].row == (i+1))
                  temp.add(nodesC[j]);
            }
            
            if (temp.size() == 0)
               rowC[i] = new Node (rowC[i], rowC[i].colLink, rowC[i].value, rowC[i].row, rowC[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowC[i], t.colLink, t.value, t.row, t.col);
               rowC[i] = new Node (t, rowC[i].colLink, rowC[i].value, rowC[i].row, rowC[i].col);
               for (int e = 0; e < nodesC.length; e++)
               {
                  if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                  {
                     nodesC[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowC[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesC[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                        }
                     }
                     rowC[i] = new Node(t, rowC[i].colLink, rowC[i].value, rowC[i].row, rowC[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesC[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesC.length; j++)
            {
               if (nodesC[j].col == (i+1))
                  temp.add(nodesC[j]);
            }
            
            if (temp.size() == 0)
               colC[i] = new Node (colC[i].rowLink, colC[i], colC[i].value, colC[i].row, colC[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colC[i], t.value, t.row, t.col);
               colC[i] = new Node (colC[i].rowLink, t, colC[i].value, colC[i].row, colC[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colC[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesC[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                        }
                     }
                     colC[i] = new Node(colC[i].rowLink, t, colC[i].value, colC[i].row, colC[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesC[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesC.length; e++)
                     {
                        if (t.value == nodesC[e].value && t.row == nodesC[e].row && t.col == nodesC[e].col)
                        {
                           nodesC[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      //System.out.println(rowC[0].rowLink.rowLink);
      result[1] = new SparseMatrix(rowC, colC, n);
      
      //Matrix D
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      for (int i = 1; i < n+1; i++)
      {
         for (int j = 1; j < n+1; j++)
         {
            if ( j == 3 )
            {
               Node nd = new Node(null, null, (-i), i, j);
               tp.add(nd);
            }
            else if ( i%2 == 1 && j%2 == 1)
            {
               Node nd = new Node(null, null, (i+j), i, j);
               tp.add(nd);
            }
         }
      }
      nodesD = new Node[tp.size()];
      for (int x = 0; x < tp.size(); x++)
      {
         nodesD[x] = tp.get(x);
      }
      
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      //System.out.println(colD[0].colLink.colLink.colLink);
      result[2] = new SparseMatrix(rowD, colD, n);
      return result;
   }
   
   public void print() 
   {
      for (int i = 0; i < size; i++)
      {
         if (rowHeads[i].rowLink == rowHeads[i])
         {
            for (int j = 0; j < size; i++)
               System.out.print(0 + " ");
         }
         else
         {
            Node it = rowHeads[i];
            int place = 0;
            while (true)
            {
               it = it.rowLink;
               if (it == null || it == rowHeads[i] || it.value == 0.0)
                  break;
               for (int j = place; j < it.col-1; j++)
                  System.out.print(0 + " "); 
               System.out.print(it.value + " ");
               place = it.col;
            }
            if (place != size)
               for (int j = place; j < size; j++)
                  System.out.print(0 + " ");
         }
         
         System.out.println("");   
      }
   }
   
   //parameter m --> another sparse matrix m
   public SparseMatrix add(SparseMatrix m) {
      int n = this.size;
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      Node[] nodesD;
      int count = 0;
      SparseMatrix result = this;
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      
      
      for(int i = 0; i < n; i++)
      {
         int flag;
         Node temp = this.rowHeads[i].rowLink;
         Node temp2 = m.rowHeads[i].rowLink;
         Node tt = m.rowHeads[i].rowLink;
         while (temp.col != 0)
         {
            while (tt.col != 0)
            {
               if ( (temp.value + tt.value) == 0 && temp.dimensions(tt))
               {
                  Node t = new Node(null, null, 0, temp.row, temp.col);
                  count ++;
                  tp.add(t);
               }   
               else if (temp.col == tt.col)
               {
                  Node t = new Node(null, null, temp.value+tt.value, temp.row, temp.col);
                  tp.add(t);
               }
               tt = tt.rowLink;
            }
         flag = 0;
         for (int x = 0; x < tp.size(); x++)
         {
            if (temp.dimensions(tp.get(x)) )
               flag = 1;
         }
         if (flag == 0)
         {
            Node tmp = new Node (null, null, temp.value, temp.row, temp.col);
            tp.add(tmp);
         }   
         temp = temp.rowLink;
         tt = temp2;
            
         }
         while (tt.col != 0)
         {
            flag = 0;
            for (int x = 0; x < tp.size(); x++)
            {
               if (tt.dimensions(tp.get(x)))
               {
                  flag = 1;  
               }   
            }
            if (flag == 0)
            {
               Node tmp = new Node(null, null, tt.value, tt.row, tt.col);
               tp.add(tmp);
            }  
            tt = tt.rowLink;   
         }
      }
      //System.out.println("----------------------------------------");
      nodesD = new Node [tp.size()-count];
      int co = 0;
      int ind = 0;
      while (co < tp.size())
      {
         if ( (tp.get(co)).value != 0)
         {
            nodesD[ind] = tp.get(co);
            ind++;
         }
         co ++;
      } 
      for (int i = 0; i < nodesD.length; i++)
      {
         for (int j = 1; j < nodesD.length; j++)
         {
            if (nodesD[j-1].greater(nodesD[j]))
            {
               Node temp = nodesD[j-1];
               nodesD[j-1] = nodesD[j];
               nodesD[j] = temp;    
            }
         }
      }

      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      result = new SparseMatrix(rowD, colD, n);
      return result;
   }
   
   //parameter m --> another sparse matrix m
   public SparseMatrix subtract(SparseMatrix m) {
      int n = this.size;
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      Node[] nodesD;
      int count = 0;
      SparseMatrix result = this;
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      
      
      for(int i = 0; i < n; i++)
      {
         int flag;
         Node temp = this.rowHeads[i].rowLink;
         Node temp2 = m.rowHeads[i].rowLink;
         Node tt = m.rowHeads[i].rowLink;
         while (temp.col != 0)
         {
            while (tt.col != 0)
            {
               if ( (temp.value - tt.value) == 0 && temp.dimensions(tt))
               {
                  Node t = new Node(null, null, 0, temp.row, temp.col);
                  count ++;
                  tp.add(t);
               }   
               else if (temp.col == tt.col)
               {
                  Node t = new Node(null, null, temp.value-tt.value, temp.row, temp.col);
                  tp.add(t);
               }
               tt = tt.rowLink;
            }
         flag = 0;
         for (int x = 0; x < tp.size(); x++)
         {
            if (temp.dimensions(tp.get(x)) )
               flag = 1;
         }
         if (flag == 0)
         {
            Node tmp = new Node (null, null, temp.value, temp.row, temp.col);
            tp.add(tmp);
         }   
         temp = temp.rowLink;
         tt = temp2;
            
         }
         while (tt.col != 0)
         {
            flag = 0;
            for (int x = 0; x < tp.size(); x++)
            {
               if (tt.dimensions(tp.get(x)))
               {
                  flag = 1;  
               }   
            }
            if (flag == 0)
            {
               Node tmp = new Node(null, null, 0-tt.value, tt.row, tt.col);
               tp.add(tmp);
            }  
            tt = tt.rowLink;   
         }
      }
      nodesD = new Node [tp.size()-count];
      int co = 0;
      int ind = 0;
      while (co < tp.size())
      {
         if ( (tp.get(co)).value != 0)
         {
            nodesD[ind] = tp.get(co);
            ind++;
         }
         co ++;
      } 
      for (int i = 0; i < nodesD.length; i++)
      {
         for (int j = 1; j < nodesD.length; j++)
         {
            if (nodesD[j-1].greater(nodesD[j]))
            {
               Node temp = nodesD[j-1];
               nodesD[j-1] = nodesD[j];
               nodesD[j] = temp;    
            }
         }
      }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      result = new SparseMatrix(rowD, colD, n);
      return result;

   }
   
   //integer a
   public SparseMatrix scalarMultiply(int a) {
      int n = this.size;
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      Node[] nodesD;
      SparseMatrix result = this;
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      for(int i = 0; i < n; i++)
      {
         Node temp = this.rowHeads[i].rowLink;
         while (temp.col != 0)
         {
            Node tmp = new Node (null, null, temp.value*a, temp.row, temp.col);
            tp.add(tmp); 
            temp = temp.rowLink;
            
         }
      }
      nodesD = new Node [tp.size()];
      for (int i = 0; i < nodesD.length; i++)
      {
         nodesD[i] = tp.get(i);
      } 
      // for (int i = 0; i < nodesD.length; i++)
//       {
//          for (int j = 1; j < nodesD.length; j++)
//          {
//             if (nodesD[j-1].greater(nodesD[j]))
//             {
//                Node temp = nodesD[j-1];
//                nodesD[j-1] = nodesD[j];
//                nodesD[j] = temp;    
//             }
//          }
//       }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      result = new SparseMatrix(rowD, colD, n);
      return result;
   }
   
   //parameter m --> another sparse matrix m
   public SparseMatrix matrixMultiply(SparseMatrix m) {
      SparseMatrix result = this;
      int n = this.size;
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      Node[] nodesD;
      int value;
      int flag;
      int colu;
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      for(int i = 0; i < n; i++)
      {
         colu = 0;
         for (int j = 0; j < n; j++)
         {
            Node temp = this.rowHeads[i].rowLink;
            Node temp2 = m.colHeads[j].colLink;
            Node tt = m.colHeads[j].colLink;
            flag = 0;
            value = 0;
            while(temp.col != 0)
            {
               while (tt.row != 0)
               {
                  if (temp.col == tt.row)
                  {
                     value += (temp.value*tt.value);
                     flag = 1;
                     colu = j+1;
                  }   
                  tt = tt.colLink;
               }
               tt = temp2;
               temp = temp.rowLink;
            }
            if (flag == 1)
            {
               Node nd = new Node(null, null, value, i+1, colu);
               tp.add(nd);
               //System.out.println(nd);
            }
         } 
      }  
      nodesD = new Node [tp.size()];
      for (int i = 0; i < nodesD.length; i++)
      {
         nodesD[i] = tp.get(i);
      } 
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      result = new SparseMatrix(rowD, colD, n);
      return result;
   }
   
   //integer c
   public SparseMatrix power(int c) {
      SparseMatrix result = this;
      SparseMatrix y = this;
      int turn = 0;
      if (c == 1)
         return result;
      else 
      {
          while (c > 1)
          {
            if (c%2 == 0)
            {
               result = result.matrixMultiply(result);
               c = c/2;
            }
            else
            {
               if (turn == 0)
               {
                  y = result;
               }  
               else
               {
                  y = result.matrixMultiply(y);
               } 
               result = result.matrixMultiply(result);
               c = (c-1)/2;
               turn++;
            }
          }
      }    
      result = result.matrixMultiply(y);
      return result;
   }
    
   //transpose matrix itself
   public SparseMatrix transpose() {
      SparseMatrix result = this;
      int n = this.size;
      Node[] rowD = new Node[n];
      Node[] colD = new Node[n];
      Node[] nodesD;
      ArrayList<Node> tp = new ArrayList<Node>();
      for (int i = 0; i < n; i++)
      {
         rowD[i] = new Node(null, null, 0, i+1, 0);
         colD[i] = new Node(null, null, 0, 0, i+1);
      } 
      for(int i = 0; i < n; i++)
      {
         Node temp = this.rowHeads[i].rowLink;
         while(temp.col != 0)
         {
            Node nd = new Node(null, null, temp.value, temp.col, temp.row);
            tp.add(nd);
            temp = temp.rowLink;
         }
      } 
      nodesD = new Node [tp.size()];
      for (int i = 0; i < nodesD.length; i++)
      {
         nodesD[i] = tp.get(i);
      } 
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].row == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               rowD[i] = new Node (rowD[i], rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
               rowD[i] = new Node (t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
               for (int e = 0; e < nodesD.length; e++)
               {
                  if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                  {
                     nodesD[e] = t;
                  }
               }
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (rowD[i], t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     rowD[i] = new Node(t, rowD[i].colLink, rowD[i].value, rowD[i].row, rowD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (s, t.colLink, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      for (int i = 0; i < n; i++)
         {
            ArrayList<Node> temp = new ArrayList<Node>();
            for (int j = 0; j < nodesD.length; j++)
            {
               if (nodesD[j].col == (i+1))
                  temp.add(nodesD[j]);
            }
            
            if (temp.size() == 0)
               colD[i] = new Node (colD[i].rowLink, colD[i], colD[i].value, colD[i].row, colD[i].col);
            else if (temp.size() == 1)
            {
               Node t = temp.get(0);
               t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
               colD[i] = new Node (colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
            }
            else
            {
               int save = 0;
               for (int k = temp.size()-1; k >= 0; k--)
               {
                  if ( k == temp.size()-1 )
                  {
                     Node t = temp.get(k);
                     t = new Node (t.rowLink, colD[i], t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
                  else if ( k == 0 )
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                        }
                     }
                     colD[i] = new Node(colD[i].rowLink, t, colD[i].value, colD[i].row, colD[i].col);
                  }
                  else
                  {
                     Node t = temp.get(k);
                     Node s = nodesD[save];
                     t = new Node (t.rowLink, s, t.value, t.row, t.col);
                     for (int e = 0; e < nodesD.length; e++)
                     {
                        if (t.value == nodesD[e].value && t.row == nodesD[e].row && t.col == nodesD[e].col)
                        {
                           nodesD[e] = t;
                           save = e;
                        }
                     }
                  }
               }
            }
         }
      result = new SparseMatrix(rowD, colD, n);
      return result;
   }
   
   public int getSize(){
      return size;
   }
 
}
