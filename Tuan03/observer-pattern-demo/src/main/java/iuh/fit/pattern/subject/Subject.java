package iuh.fit.pattern.subject;


import iuh.fit.pattern.observer.Observer;

public interface Subject {
    void registerObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}