package it.univr.Programmazione.Dama.controller;

@SuppressWarnings("serial")
public class MoveException extends RuntimeException {
	
	public MoveException() {
		super();
	}
	
	public MoveException(String message) {
		super(message);
	}

}