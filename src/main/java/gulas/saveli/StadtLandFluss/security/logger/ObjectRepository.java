package gulas.saveli.StadtLandFluss.security.logger;

public interface ObjectRepository<T> {

    public void store(String token,T t);

    public T retrieve(String token);

    public boolean search(String name);

    public T delete(String token);
}
