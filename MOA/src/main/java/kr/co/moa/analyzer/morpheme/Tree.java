package kr.co.moa.analyzer.morpheme;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import org.trie4j.bv.SuccinctBitVector;
/*
 * 2015-12-26
 * Content Block Tree, Topic Tree�� ����� ���� tree
 * Remove noise�� �ϱ� ���� ����.
 * 
 * ������ BFS �� ���� ��尡 ���� �Ѵ�. �ƴϸ� ����.
 * Author by dongyoung  
 * */
public class Tree {
	private class Cash{
		int index;
		String name;
		String grand_name;
		
		public Cash(int i, String n){
			index = i;
			name = n;
			grand_name = "ROOT";
		}
	};
	
	private Node root;
	private List<Cash> cashing;
	private Queue<Cash> subCashing;
	
	public Tree(){
		root = new Node("ROOT");
		cashing = new ArrayList<Cash>();
		subCashing = new LinkedList<Cash>();
	}
	
	public void addNode(String parent, Node child){
		//parent�� root�϶�
		if(parent.equals("ROOT")){
			root.setChild(child);
		}else{
			//parent�� ã�´�.
			Node p = findNode(root, parent);			
			p.setChild(child);
		}	
	}
	private Node findNode(Node root, String parent){
		//cashing ������
		if(cashing.size() > 0 && parent.equals(cashing.get(cashing.size()-1).name)){
			return this.getNode(root, 0);
		}else{			
			//cashing ������ ���� Ȯ��
			if(cashing.size() == 0){
				makeCash(root,parent);
			}else{
				//���� level�� ����� ���
				
				Node node = getNode(root, 0);			
				for(Node n : node.child_list){
					if(parent.equals(n.name)){
						makeCash(node,parent);
						//test_print();
						return this.getNode(root, 0);
					}
				}
				//�ٸ� parent�� ����� ���		
				Cash c = cashing.get(cashing.size()-1);
				subCashing.add(c);
				cashing.remove(cashing.size()-1);				
				node = getNode(root, 0);
				for(Node n : node.child_list){
					if(parent.equals(n.name)){
						makeCash(node,parent);
						//test_print();
						return this.getNode(root, 0);
					}
				}
				//�θ� ���� �ִ� ���
				if(subCashing.size() == 0){
					System.err.println("subcashing err");
					return null;
				}
				cashing.add(subCashing.poll());
				return findNode(root, parent);
			}	
			return this.getNode(root, 0);
		}							
	}
	private void makeCash(Node node, String parent){
		for(int i=0; i<node.child_list.size(); i++){
			if(node.child_list.get(i).name.equals(parent)){
				Cash c = new Cash(i,parent);
				cashing.add(c);
				return;
			}
		}
	}
	private Node getNode(Node node, int idx){
		if(idx == cashing.size()-1){
			return node.child_list.get(cashing.get(idx).index);
		}
		node = node.child_list.get(cashing.get(idx).index);
		return getNode(node, idx+1);		
	}	
	public void deleteNode(String parent, String target){
		//parent�� root�϶�
		if(parent.equals("ROOT")){
			
			root.delChild(target);
		}else{
			//parent�� ã�´�.
			Node p = findDelNode(root, parent);
			if(p == null){
				System.err.println("findDelNode err");
				return;
			}
			p.delChild(target);
		}	
	}	
	private Node findDelNode(Node node, String parent){
		if(parent.equals(node.name)){
			return node;
		}
		Node tmp;
		for(Node child : node.child_list){
			tmp = findDelNode(child,parent);
			if(tmp != null) return tmp;
		}
		return null;
	}
	private void test_print(){
		System.out.println("size :"+cashing.size());
		for(Cash c: cashing){
			System.out.println(c.name + c.index);
		}					
	}
	public void print(){
		this.print_tree(root, 0);
		
	}
	public void print_tree(Node node, int level){
		if(node.child_list.size() == 0)
			return;
		if(level == 0)System.out.println(node.name+" level:" +level+++" ");
		for(Node n : node.child_list){
			System.out.print(n.name+" level:"+level+" ");
		}
		System.out.println("");
		for(Node n : node.child_list){
			if(n.child_list.size() !=0 ){
				for(int i=0; i<level; i++)
					System.out.print("\t");
				System.out.print(n.name+" child: ");
			}
				
			print_tree(n,level+1);
		}
	}

}
