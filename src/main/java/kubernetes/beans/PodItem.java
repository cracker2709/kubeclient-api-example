package kubernetes.beans;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class PodItem {
    private String name;
    private String image;
    private String version;
}
