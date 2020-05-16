using System;
using System.Diagnostics;
using System.Threading;


namespace Lab1
{
    /**
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     *                                                             *
     *            Parallel and Distributed Computing               *
     *                  Laboratory work #2. C#                     *
     *      Semaphores, Mutexes, Critical Sections, Events         *
     *                                                             *
     * Task: MA = min(Z)*MS - d*MT*MR                              *
     *                                                             *
     * Komisarov Illia                                             *
     * group IO-71                                                 *
     *                                                             *
     * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
     */

    internal class Program
    {
        public const int N = 10;
        public const int P = 3;
        public const int H = N / P;
        public static EventWaitHandle Evn_input_3;
        public static EventWaitHandle Evn_input_1;
        public static EventWaitHandle Evn_min_Z1;
        public static EventWaitHandle Evn_min_Z2;
        public static EventWaitHandle Evn_min_Z3;
        public static object lock_dZ = new object();
        public static Mutex mtx = new Mutex(false);
        public static Semaphore sem2, sem3;
        public static int[] Z = new int[N];
        public static int d;
        public static int min_Z = N+1;
        public static int[,] MA = new int[N, N];
        public static int[,] MT = new int[N, N];
        public static int[,] MR = new int[N, N];
        public static int[,] MS = new int[N, N];
        
        public static void Main(string[] args)
        {
            mtx = new Mutex(false);
            Evn_input_1 = new ManualResetEvent(false);
            Evn_input_3 = new ManualResetEvent(false);
            Evn_min_Z1 = new ManualResetEvent(false);
            Evn_min_Z2 = new ManualResetEvent(false);
            Evn_min_Z3 = new ManualResetEvent(false);
            
            sem2 = new Semaphore(0, 1);
            sem3 = new Semaphore(0, 1);

            Thread t1 = new Thread(T1);
            Thread t2 = new Thread(T2);
            Thread t3 = new Thread(T3);

            t1.Start();
            t2.Start();
            t3.Start();
        }

        static void T1()
        {
            Console.WriteLine("T1 started");
            // Stopwatch stopWatch = new Stopwatch();
            // stopWatch.Start();
            InputVector(Z);
            InputMatrix(MR);
            Evn_input_1.Set();
            Evn_input_3.WaitOne();
            
            int min_Z1 = Min(Z, 0, H);
            min_Z = Min(min_Z, min_Z1);
            Evn_min_Z1.Set();
            Evn_min_Z2.WaitOne();
            Evn_min_Z3.WaitOne();
            
            mtx.WaitOne();
            int[,] MT1 = MT;
            mtx.ReleaseMutex();
            
            int Z1_min, d1;
            lock (lock_dZ)
            {
                Z1_min = min_Z;
                d1 = d;
            }
            
            Calculate(Z1_min, d1, MT1, 0, H);
            sem2.WaitOne();
            sem3.WaitOne();
            
            // stopWatch.Stop();
            // TimeSpan ts = stopWatch.Elapsed;
            // // Format and display the TimeSpan value.
            // string elapsedTime = String.Format("{0:00}:{1:00}:{2:00}.{3:00}",
            //     ts.Hours, ts.Minutes, ts.Seconds,
            //     ts.Milliseconds / 10);
            // Console.WriteLine("RunTime " + elapsedTime);
            OutputMatrix(MA);
            Console.WriteLine("T1 finished");
        }
        static void T2()
        {
            Console.WriteLine("T2 started");
            Evn_input_3.WaitOne();
            Evn_input_1.WaitOne();
            
            int min_Z2 = Min(Z, H, 2*H);
            min_Z = Min(min_Z, min_Z2);
            Evn_min_Z2.Set();
            Evn_min_Z1.WaitOne();
            Evn_min_Z3.WaitOne();
            
            mtx.WaitOne();
            int[,] MT2 = MT;
            mtx.ReleaseMutex();
            
            int Z2_min, d2;
            lock (lock_dZ)
            {
                Z2_min = min_Z;
                d2 = d;
            }
            
            Calculate(Z2_min, d2, MT2, H, 2*H);
            sem2.Release();
            Console.WriteLine("T2 finished");
        }
        static void T3()
        {
            Console.WriteLine("T3 started");
            d = 1;
            InputMatrix(MS);
            InputMatrix(MT);
            Evn_input_3.Set();
            Evn_input_1.WaitOne();
            
            int min_Z3 = Min(Z, 2*H, N);
            min_Z = Min(min_Z, min_Z3);
            Evn_min_Z3.Set();
            Evn_min_Z1.WaitOne();
            Evn_min_Z2.WaitOne();

            mtx.WaitOne();
            int[,] MT3 = MT;
            mtx.ReleaseMutex();
            
            int Z3_min, d3;
            lock (lock_dZ)
            {
                Z3_min = min_Z;
                d3 = d;
            }
            
            Calculate(Z3_min, d3, MT3, 2*H, N);
            sem3.Release();
            Console.WriteLine("T3 finished");
        }
        
        static void InputMatrix(int[,] matrix)
        {
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    matrix[i, j] = 1;
                }
            }
        }
        static void InputVector(int[] vector)
        {
            Random rand = new Random();
            for (int i = 0; i < vector.Length; i++)
            {
                vector[i] = i+1;
            }
            Shuffle(rand, vector);
        }
        static void OutputMatrix(int[,] matrix)
        {
            for (int i = 0; i < N; i++)
            {
                for (int j = 0; j < N; j++)
                {
                    Console.Write($"{matrix[i, j]} ");
                }

                Console.WriteLine();
            }
        }
        
        static void OutputVector(int[] vector)
        {
            Console.Write("[");
            for (int i = 0; i < vector.Length; i++)
            {
                Console.Write(i == vector.Length - 1 ? $"{vector[i]}" : $"{vector[i]}, ");
            }
            Console.WriteLine("]");
        }

        static void Calculate(int min_Z_i, int d_i, int[,] MT_i, int left, int right)
        {
            for (int i = 0; i < N; i++)
            {
                for (int j = left; j < right; j++)
                {
                    int sum = 0;
                    for (int k = 0; k < N; k++)
                    {
                        sum += MT_i[i, k] * MR[k, j];
                    }
                    MA[i, j] = min_Z_i * MS[i, j] - d_i*sum;
                }
            }
        }

        static int Min(int[] vector, int left, int right)
        {
            int min = vector[left];
            for (int i = left+1; i < right; i++)
            {
                if (vector[i] < min)
                {
                    min = vector[i];
                }
            }

            return min;
        }
        static int Min(int min_Z, int min_ZH)
        {
            return min_Z < min_ZH ? min_Z : min_ZH;
        }
        public static void Shuffle (Random rng, int[] array)
        {
            int n = array.Length;
            while (n > 1) 
            {
                int k = rng.Next(n--);
                int temp = array[n];
                array[n] = array[k];
                array[k] = temp;
            }
        }
    }
}