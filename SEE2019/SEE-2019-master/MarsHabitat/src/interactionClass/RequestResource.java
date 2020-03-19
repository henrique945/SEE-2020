package interactionClass;

import objectClass.Resource;
import objectClass.ResourceCoder;
import skf.model.interaction.annotations.InteractionClass;
import skf.model.interaction.annotations.Parameter;

/**
 * Created by Lucas on 21/03/2017.
 */
@InteractionClass(name="RequestResource")
public class RequestResource {

    @Parameter(name = "resource",coder = ResourceCoder.class)
    private Resource resource = null;

    public RequestResource(){

    }

    public RequestResource(Resource resource){
        this.setResource(resource);
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

	@Override
	public String toString() {
		return "RequestResource [resource=" + resource + "]";
	}
    
}
