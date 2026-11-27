/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dbms.Arbol;

import java.util.ArrayList;

/**
 *
 * @author starwalkerholiday
 */
public class BNode {
    
    private ArrayList<Integer> keys = new ArrayList<Integer>();
    private ArrayList<BNode> children = new ArrayList<BNode>();
    private boolean is_leaf;
    private int t;
    
    // muchos de los métodos requieren (nodo, posición). pos sirve para guardar esa posición.
    private int pos = 0;
    
    // cantidad de llaves = keys.size();
    // cantidad de hijos = children.size();
    
    public BNode(int t, boolean is_leaf) {
        this.t = t;
        this.is_leaf = is_leaf;
    }
    
    int getKeyInPos() {
        return keys.get(pos);
    }
    
    void searchInNode(int k) { //coloca pos en k, si lo encuentra
        pos = 0;
        while (pos < keys.size() && k > keys.get(pos)) {
            pos++;
        }
    }
    
    BNode search(int k) {
        searchInNode(k);
        if (pos < keys.size() && keys.get(pos) == k) {
            return this;
        }
        if (is_leaf) {
            return null;
        }
        else {
            return children.get(pos).search(k);
        }
    }
    
    private void split(int i) {
        BNode child = children.get(i);
        BNode promoted_node = new BNode(t, false);
        int median = (t-1)/2;
        
    }
    
}
