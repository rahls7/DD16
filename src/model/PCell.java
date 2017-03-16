package model;


public class PCell {
    private int x, y;
    private String type = "";
    private PCellContent content;

    public PCell(int i, int j, String content) {
        x = i;
        y = j;

        if(content != null){
            String[] parts = content.split(" ");
            type = parts[0];

            if(type.equals("CHARACTER")){
                this.content = new PCharacter(parts[1], parts[2]);
            }
            else if(type.equals("CHEST"))
                this.content = new PChest(parts[1]);
            else
                this.content = new PConstant(type);
        }
    }
    public void setType(String type) { this.type = type; }

    public String getType() { return type; }

    public void setPlayer(PCharacter player) {
        if(type.equals("") || type.equals("ENTRY")) {
            content = player;
            type = "PLAYER";
        }
    }

    public void removePlayer() {
        content = new PConstant("");
        type = "";
    }

    public PCellContent getContent() { return content; }

}
