package object_orinted_design.file_search;

import java.util.Set;

public interface FileSearchIndex {

    public void index(FileName file);

    public void purge(String fileName);

    public void update(String fileName);

    public void load(String path);

    public Set<String> search(String keyword);

    public void reindex(SortType sortType);

}
