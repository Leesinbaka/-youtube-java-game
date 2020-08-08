filp the 8*8 

if(y+yPos <0 || y+yPos >=height)continue;//step 2 如果y+yPos <0 or y+yPos >=height 就無限增大的意思 set bounds
     int ySheet = y;//step 1
     if(mirrorY)ySheet = 7-y;//filp it XD
     for(int x = 0;x<8;x++){
         if(x+xPos <0 || x+xPos >=width)continue;//step 2 
         int xSheet = x;//step 1
         if(mirrorX)xSheet = 7-x;//filp it XD


add boolean mirrorY mirrorX in function Screen.java render()