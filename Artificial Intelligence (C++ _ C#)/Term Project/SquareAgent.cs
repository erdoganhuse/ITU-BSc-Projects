

using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

using Microsoft.Xna.Framework;
using GeometryFriends.AI.Interfaces;

namespace GeometryFriendsAgents
{
    class SquareAgent : ISquareAgent
    {
        private bool implementedAgent;
        private int lastAction;
        private int currentAction;
        private long lastMoveTime;
        private Random rnd;

        //Sensors Information
        private int[] numbersInfo;
        private float[] squareInfo;
        private float[] circleInfo;
        private float[] obstaclesInfo;
        private float[] squarePlatformsInfo;
        private float[] circlePlatformsInfo;
        private float[] collectiblesInfo;

        private int nCollectiblesLeft;

        private string agentName = "RandRect";

        //Area of the game screen
        protected Rectangle area;

        public SquareAgent()
        {
            //Change flag if agent is not to be used
            SetImplementedAgent(true);

            lastMoveTime = DateTime.Now.Millisecond % 100;
            lastAction = 0;
            currentAction = 0;
            rnd = new Random();
        }

        public void Setup(int[] nI, float[] sI, float[] cI, float[] oI, float[] sPI, float[] cPI, float[] colI, Rectangle area, double timeLimit) {
            // Time limit is the time limit of the level - if negative then there is no time constrains
            this.area = area;
            int temp;

            // numbersInfo[] Description
            //
            // Index - Information
            //
            //   0   - Number of Obstacles
            //   1   - Number of Square Platforms
            //   2   - Number of Circle Platforms
            //   3   - Number of Collectibles

            numbersInfo = new int[4];
            int i;
            for (i = 0; i < nI.Length; i++)
            {
                numbersInfo[i] = nI[i];

            }

            nCollectiblesLeft = nI[3];

            // squareInfo[] Description
            //
            // Index - Information
            //
            //   0   - Square X Position
            //   1   - Square Y Position
            //   2   - Square X Velocity
            //   3   - Square Y Velocity
            //   4   - Square Height

            squareInfo = new float[5];

            squareInfo[0] = sI[0];
            squareInfo[1] = sI[1];
            squareInfo[2] = sI[2];
            squareInfo[3] = sI[3];
            squareInfo[4] = sI[4];

            // circleInfo[] Description
            //
            // Index - Information
            //
            //   0  - Circle X Position
            //   1  - Circle Y Position
            //   2  - Circle X Velocity
            //   3  - Circle Y Velocity
            //   4  - Circle Radius

            circleInfo = new float[5];

            circleInfo[0] = cI[0];
            circleInfo[1] = cI[1];
            circleInfo[2] = cI[2];
            circleInfo[3] = cI[3];
            circleInfo[4] = cI[4];


            // Obstacles and Platforms Info Description
            //
            //  X = Center X Coordinate
            //  Y = Center Y Coordinate
            //
            //  H = Platform Height
            //  W = Platform Width
            //
            //  Position (X=0,Y=0) = Left Superior Corner

            // obstaclesInfo[] Description
            //
            // Index - Information
            //
            // If (Number of Obstacles > 0)
            //  [0 ; (NumObstacles * 4) - 1]      - Obstacles' info [X,Y,H,W]
            // Else
            //   0                                - 0
            //   1                                - 0
            //   2                                - 0
            //   3                                - 0

            if (numbersInfo[0] > 0)
                obstaclesInfo = new float[numbersInfo[0] * 4];
            else obstaclesInfo = new float[4];

            temp = 1;
            if (nI[0] > 0)
            {
                while (temp <= nI[0])
                {
                    obstaclesInfo[(temp * 4) - 4] = oI[(temp * 4) - 4];
                    obstaclesInfo[(temp * 4) - 3] = oI[(temp * 4) - 3];
                    obstaclesInfo[(temp * 4) - 2] = oI[(temp * 4) - 2];
                    obstaclesInfo[(temp * 4) - 1] = oI[(temp * 4) - 1];
                    temp++;
                }
            }
            else
            {
                obstaclesInfo[0] = oI[0];
                obstaclesInfo[1] = oI[1];
                obstaclesInfo[2] = oI[2];
                obstaclesInfo[3] = oI[3];
            }

            // squarePlatformsInfo[] Description
            //
            // Index - Information
            //
            // If (Number of Square Platforms > 0)
            //  [0; (numSquarePlatforms * 4) - 1]   - Square Platforms' info [X,Y,H,W]
            // Else
            //   0                                  - 0
            //   1                                  - 0
            //   2                                  - 0
            //   3                                  - 0


            if (numbersInfo[1] > 0)
                squarePlatformsInfo = new float[numbersInfo[1] * 4];
            else
                squarePlatformsInfo = new float[4];

            temp = 1;
            if (nI[1] > 0)
            {
                while (temp <= nI[1])
                {
                    squarePlatformsInfo[(temp * 4) - 4] = sPI[(temp * 4) - 4];
                    squarePlatformsInfo[(temp * 4) - 3] = sPI[(temp * 4) - 3];
                    squarePlatformsInfo[(temp * 4) - 2] = sPI[(temp * 4) - 2];
                    squarePlatformsInfo[(temp * 4) - 1] = sPI[(temp * 4) - 1];
                    temp++;
                }
            }
            else
            {
                squarePlatformsInfo[0] = sPI[0];
                squarePlatformsInfo[1] = sPI[1];
                squarePlatformsInfo[2] = sPI[2];
                squarePlatformsInfo[3] = sPI[3];
            }

            // circlePlatformsInfo[] Description
            //
            // Index - Information
            //
            // If (Number of Circle Platforms > 0)
            //  [0; (numCirclePlatforms * 4) - 1]   - Circle Platforms' info [X,Y,H,W]
            // Else
            //   0                                  - 0
            //   1                                  - 0
            //   2                                  - 0
            //   3                                  - 0

            if (numbersInfo[2] > 0)
                circlePlatformsInfo = new float[numbersInfo[2] * 4];
            else
                circlePlatformsInfo = new float[4];

            temp = 1;
            if (nI[2] > 0)
            {
                while (temp <= nI[2])
                {
                    circlePlatformsInfo[(temp * 4) - 4] = cPI[(temp * 4) - 4];
                    circlePlatformsInfo[(temp * 4) - 3] = cPI[(temp * 4) - 3];
                    circlePlatformsInfo[(temp * 4) - 2] = cPI[(temp * 4) - 2];
                    circlePlatformsInfo[(temp * 4) - 1] = cPI[(temp * 4) - 1];
                    temp++;
                }
            }
            else
            {
                circlePlatformsInfo[0] = cPI[0];
                circlePlatformsInfo[1] = cPI[1];
                circlePlatformsInfo[2] = cPI[2];
                circlePlatformsInfo[3] = cPI[3];
            }

            //Collectibles' To Catch Coordinates (X,Y)
            //
            //  [0; (numCollectibles * 2) - 1]   - Collectibles' Coordinates (X,Y)

            collectiblesInfo = new float[numbersInfo[3] * 2];

            temp = 1;
            while (temp <= nI[3])
            {

                collectiblesInfo[(temp * 2) - 2] = colI[(temp * 2) - 2];
                collectiblesInfo[(temp * 2) - 1] = colI[(temp * 2) - 1];

                temp++;
            }

            DebugSensorsInfo();

        }

        private void SetImplementedAgent(bool b)
        {
            implementedAgent = b;
        }

        public void setAgentPane(GeometryFriends.AgentDebugPane aP)
        {
            //this.agentPane = aP;
        }

        public bool ImplementedAgent()
        {
            return implementedAgent;
        }

        private void SetAction(int a)
        {
            currentAction = a;
        }

        //Manager gets this action from agent
        public int GetAction()
        {
            return currentAction;
        }

        public void UpdateSensors(int nC, float[] sI, float[] cI, float[] colI)
        {
            int temp;

            nCollectiblesLeft = nC;

            squareInfo[0] = sI[0];
            squareInfo[1] = sI[1];
            squareInfo[2] = sI[2];
            squareInfo[3] = sI[3];
            squareInfo[4] = sI[4];

            circleInfo[0] = cI[0];
            circleInfo[1] = cI[1];
            circleInfo[2] = cI[2];
            circleInfo[3] = cI[3];
            circleInfo[4] = cI[4];


            Array.Resize(ref collectiblesInfo, (nCollectiblesLeft * 2));

            temp = 1;
            while (temp <= nCollectiblesLeft)
            {

                collectiblesInfo[(temp * 2) - 2] = colI[(temp * 2) - 2];
                collectiblesInfo[(temp * 2) - 1] = colI[(temp * 2) - 1];

                temp++;
            }

        }

        private void go_to_coordinate(float x, float y)
        {
            SetAction(Moves.MORPH_DOWN);
            if (Math.Abs(squareInfo[2]) > 170)
            {
                SetAction(Moves.NO_ACTION);
                lastAction = 0;
            }
            else if (squareInfo[0] < x - 20)
            {
                if (Math.Abs(squareInfo[2]) < 5 && (lastAction == 6 || lastAction == 5))
                {
                    SetAction(Moves.MORPH_UP);
                    lastAction = 7;
                }
                else
                {
                    SetAction(Moves.MOVE_RIGHT);
                    lastAction = 6;
                }
            }
            else if (squareInfo[0] > x + 20)
            {
                if (Math.Abs(squareInfo[2]) < 5 && (lastAction == 6 || lastAction == 5))
                {
                    SetAction(Moves.MORPH_UP);
                    lastAction = 7;
                }
                else
                {
                    SetAction(Moves.MOVE_LEFT);
                    lastAction = 5;
                }
            }
            else if (Math.Abs(squareInfo[0] - circleInfo[0]) < 20)
            {
                SetAction(Moves.MORPH_UP);
                lastAction = 7;
            }
        }

        public void Update(TimeSpan elapsedGameTime)
        {

            //Console.WriteLine("    now = {0}   --  square last {1} ", DateTime.Now.Second, lastMoveTime);

            if (lastMoveTime == 100)
                lastMoveTime = 0;

            if ((lastMoveTime) <= (DateTime.Now.Millisecond) && (lastMoveTime < 100))
            {
                if (!(DateTime.Now.Millisecond % 100 == 99))
                {
                    go_to_coordinate(collectiblesInfo[0], collectiblesInfo[1]);
                    lastMoveTime = lastMoveTime + 1;
                    //DebugSensorsInfo();
                }
                else
                    lastMoveTime = 100;
            }
        }


        public void toggleDebug()
        {
            //this.agentPane.AgentVisible = !this.agentPane.AgentVisible;
        }

        protected void DebugSensorsInfo()
        {
            int t = 0;
            /*
            foreach (int i in numbersInfo)
            {
                Console.WriteLine("SQUARE - Numbers info - {0} - {1}", t, i);
                t++;
            }
            */

            Console.WriteLine("SQUARE - collectibles left - {0}", nCollectiblesLeft);
            Console.WriteLine("SQUARE - collectibles info size - {0}", collectiblesInfo.Count());

            /*
            t = 0;
            foreach (long i in squareInfo)
            {
                Console.WriteLine("SQUARE - Square info - {0} - {1}", t, i);
                t++;
            }

            t = 0;
            foreach (long i in circleInfo)
            {
                Console.WriteLine("SQUARE - Circle info - {0} - {1}", t, i);
                t++;
            }
            
            t = 0;
            foreach (long i in obstaclesInfo)
            {
                Console.WriteLine("SQUARE - Obstacles info - {0} - {1}", t, i);
                t++;
            }

            t = 0;
            foreach (long i in squarePlatformsInfo)
            {
                Console.WriteLine("SQUARE - Square Platforms info - {0} - {1}", t, i);
                t++;
            }

            t = 0;
            foreach (long i in circlePlatformsInfo)
            {
                Console.WriteLine("SQUARE - Circle Platforms info - {0} - {1}", t, i);
                t++;
            }
            */
            t = 0;
            foreach (float i in collectiblesInfo)
            {
                Console.WriteLine("SQUARE - Collectibles info - {0} - {1}", t, i);
                t++;
            }
            
        }
        public string AgentName()
        {
            return agentName;
        }

        public void EndGame(int collectiblesCaught, int timeElapsed) {
            Console.WriteLine("SQUARE - Collectibles caught = {0}, Time elapsed - {1}", collectiblesCaught, timeElapsed);
        }
    }
}
