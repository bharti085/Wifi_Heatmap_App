from lib2to3.pgen2.tokenize import generate_tokens
import os
import sys

#TEST_CONFIG_JSON = "config.json"
#TEST_IMAGE = "testP1.png"
from wifi_heat_mapper.graph import generate_graph
class MakeHeatMap:
    def __init__(self,TEST_IMAGE, TEST_CONFIG_JSON):
        self.img = TEST_IMAGE
        self.json = TEST_CONFIG_JSON

    def run(self):
        generate_graph(data=self.json, floor_map='./ResizedImages/'+self.img)
        print("Created")
        
        #os.system(
        #   "whm plot --map ./images/" + self.img + " --config " + self.json
        #    )


if __name__ == "__main__":
    hm = MakeHeatMap(sys.argv[1],sys.argv[2]).run()