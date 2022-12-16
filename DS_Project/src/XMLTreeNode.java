
import java.util.ArrayList;

public class XMLTreeNode {

    private String value;
    private ArrayList<XMLTreeNode> children = new ArrayList<>();

    public XMLTreeNode(String value) {
        this.value = value;
    }

    public XMLTreeNode(String value, ArrayList<XMLTreeNode> children) {
        this.value = value;
        this.children = children;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


    public ArrayList<XMLTreeNode> getChildren() {
        return children;
    }

    public void addChild(XMLTreeNode node)
    {
        this.children.add(node);
    }

}
