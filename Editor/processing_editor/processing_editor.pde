
String fileName;
int h;
int w;
String[][] map;
int ratio = 10;
int lastKey = -1;
String lastFill = "0";
boolean clicked = false;

void setup() {
  readLevel(sketchPath("")+"test.txt");
  size(w*ratio,h*ratio);
  background(0);
}

void draw() {
  noStroke();
  colorMode(RGB, 100);
  
  if (clicked) {
    try {
      map[mouseY/ratio][mouseX/ratio] = lastFill;
    } catch (ArrayIndexOutOfBoundsException e) { /* cursor out of window */ } 
  }
  
  for (int i=0; i<h; ++i) {
    for (int j=0; j<w; ++j) {
      if (map[i][j].equals("1")) { fill(100,100,100); }
      else if (map[i][j].equals("F")) { fill(100,0,0); }
      else if (map[i][j].equals("L")) { fill(0,100,0); }
      else if (map[i][j].equals("T")) { fill(0,0,100); }
      else { fill(0,0,0); }
      rect(j*width/w, i*height/h, width/w, height/h);
    }
  }
}

/**
 * Swaps the color of the given cell.
 */
String toFill(int i, int j) {
  if (lastKey == 'f') { return "F"; }
  if (lastKey == 't') { return "T"; }
  if (lastKey == 'l') { return "L"; }
  else if (map[i][j].equals("0")) { return "1"; }
  else { return "0"; }
}

void mousePressed() {
  lastFill = toFill(mouseY/ratio, mouseX/ratio);
  clicked = true;
}

void mouseReleased() {
  lastFill = "0";
  clicked = false;
}

void keyPressed() {
  lastKey = key;
  if (key == 's') { saveLevel(); }
}

void keyReleased() {
  lastKey = -1;
}

/**
 * Read level.
 * Fills data for h, w, and map.
 */
void readLevel(String name) {
  fileName = name;
  File file=new File(fileName);
  try {
    BufferedReader in = createReader(file);
    h = int(in.readLine());
    w = int(in.readLine());
    map = new String[h][w];
    String text=null;
    int count = 0;
    while ((text=in.readLine()) != null) {
      String[] newLine = split(text, ' ');
      for (int i=0; i<newLine.length; ++i) {
        map[count][i] = newLine[i];
      }
      ++count;
    }
  } catch (IOException e) {
    e.printStackTrace();
  }
}

/**
 * Saves level.
 * Reads from h, w and map.
 */
void saveLevel() {
  PrintWriter out = createWriter(fileName);
  out.println(h);
  out.println(w);
  for (String[] l : map) {
    out.println(join(l, " "));
  }
  out.flush();
  out.close();
}

