package kubernetes.beans;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class NameSpaceItem {
    private String name;
    private List<PodItem> podItems;
}
