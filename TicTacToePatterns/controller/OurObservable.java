package controller;

/** 
 * A simpler version of java's class Observable
 * 
 * @author mercer
*/

import java.util.HashSet;
import java.util.Set;

public class OurObservable {

  protected Set<OurObserver> observers = new HashSet<OurObserver>();

  public void addObserver(OurObserver observer) {
    observers.add(observer);
  }

  public void notifyObservers() {
    // Our simple Observable, the subclass is also know as 
    for (OurObserver observer : observers) {
      observer.update(this);
    }
  }
}