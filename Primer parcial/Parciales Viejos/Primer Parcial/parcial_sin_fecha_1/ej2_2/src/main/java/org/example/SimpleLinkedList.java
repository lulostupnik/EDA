package org.example;

import java.util.Iterator;
import java.util.NoSuchElementException;

// lista simplemente encadenada, no acepta repetidos (false e ignora) ni nulls (exception)
public class SimpleLinkedList<T extends Comparable<? super T>> implements  Iterable<T>{

	private Node root;
	private int size;
	private Node last;

// iterativa

	public boolean insert(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");
		
		Node prev = null;
		Node current= root;

		while( current != null ) {
			// avanzo
			prev= current;
			current= current.next;
		}


		// insercion segura
		Node aux = new Node(data, current);
		
		// como engancho??? cambia el root???
		if (current == root)
			// cambie el primero
			root= aux;
		else  // nodo interno
			prev.next= aux;

		if (aux.next == null)
			last = aux;

		size++;
		return true;
	}



	public boolean find(T data) {
		return getPos(data) != -1;
	}


	public boolean remove(T data) {
		if (data == null)
			throw new IllegalArgumentException("data cannot be null");

		Node prev = null;
		Node current = root;

		while( current != null && current.data.compareTo(data) <0) {
			// avanzo
			prev = current;
			current = current.next;
		}

		if(current != null && current.data.compareTo(data) == 0) {
			if(current == root)
				root = current.next;
			else
				prev.next = current.next;
			if(current == last)
				last = prev;
			return true;
		}
		return false;
	}


	public boolean isEmpty() {
		return root == null;
	}


	public int size() {
		return size;
	}
	

	public void dump() {
		Node current = root;

		while (current!=null ) {
			// avanzo
			System.out.println(current.data);
			current= current.next;
		}
	}
	
	@Override
	public boolean equals(Object other) {
		if (!(other instanceof SimpleLinkedList))
			return false;
		
		@SuppressWarnings("unchecked")
		SimpleLinkedList<T> auxi = (SimpleLinkedList<T>) other;
		
		Node current = root;
		Node currentOther= auxi.root;
		while (current!=null && currentOther != null ) {
			if (current.data.compareTo(currentOther.data) != 0)
				return false;
			
			// por ahora si, avanzo ambas
			current= current.next;
			currentOther= currentOther.next;
		}
		
		return current == null && currentOther == null;
		
	}
	
	// -1 si no lo encontro
	protected int getPos(T data) {
		Node current = root;
		int pos= 0;
		
		while (current!=null ) {
			if (current.data.compareTo(data) < 0) //ordenada
				return -1;

			if (current.data.compareTo(data) == 0)
				return pos;
			
			// avanzo
			current= current.next;
			pos++;
		}
		return -1;
	}
	

	@Override
	public Iterator<T> iterator() {
		return new SortedLinkedListIterator();
	}

	private final class Node {
		private final T data;
		private Node next;
		
		private Node(T data) {
			this.data= data;
		}
		
		private Node(T data, Node next) {
			this.data= data;
			this.next= next;
		}
		
		private Node insert(T data, boolean[] rta) {
			
			if (this.data.compareTo(data) == 0) {
				System.err.printf("Insertion failed %s%n", data);
				rta[0]= false;
				return this;
			}

			if(this.data.compareTo(data) < 0) {
				// soy el ultimo?
				if (next==null) {
					rta[0]= true;
					next   = new Node(data, null);
					return this;
				}
				// avanzo
				next   = next.insert(data, rta);
				return this;
			}
			
			// estoy en parado en el lugar a insertar
			rta[0]= true;
			return new Node(data, this);
		}
	}


	private class SortedLinkedListIterator implements Iterator<T> {
		private Node current = root;
		private Node last = root;
		private boolean removed = false;

		@Override
		public boolean hasNext() {
			return current!=null;
		}

		@Override
		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			removed = false;
			T toReturn = current.data;
			last = current;
			current = current.next;
			return toReturn;
		}

		@Override
		public void remove() {
			if(removed || current==null){
				throw new IllegalStateException();
			}
			removed=true;
			last.next = current.next;
			size--;
		}
	}


}
