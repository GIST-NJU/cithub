<template>
    <div class="min-height-300 bg-info position-absolute w-100"></div>
    <SideNav></SideNav>
    <main class="main-content position-relative border-radius-lg ">
        <Navbar></Navbar>

        <div class="container-fluid py-4">
            <div class="row">
                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-body pb-0 mb-4">
                            <!-- 表1 9个工具在226个模型上的执行结果 -->

                            <h6 class="text-center">Table 1: Covering Array Generation results of 9 Greedy tools on 226
                                real-world CT Model.</h6>
                            <p>Note:</p>
                            <p><b>S</b> indicates the num of models for which the tool successfully generated CA.</p>
                            <p><b>Fm</b> indicates the num of models for which generation failed due to memory exhaustion
                            </p>
                            <p><b>Ft</b> indicates the num of models for which generation failed due to time exhaustion</p>
                            <p><b>C</b> indicates the num of models for which generation failed due to tool crashes</p>
                            <p><b>I</b> indicates the the Max size of the search space tool can handle in all models,
                                expressed using logarithm with a base of 10.</p>
                            <div class='table-responsive'>
                                <table class="table table-sm ">
                                    <thead>
                                        <tr>
                                            <th colspan="1"></th>
                                            <th colspan="2"></th>
                                            <th colspan="5" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">2-way</th>
                                            <th colspan="5" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">3-way</th>
                                            <th colspan="5" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">4-way</th>
                                            <th colspan="5" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">5-way</th>
                                            <th colspan="5" class="border-left" style="border-bottom:1px solid #000000">
                                                6-way</th>
                                        </tr>
                                        <tr>
                                            <th colspan="3" style="border-bottom:1px solid #000000"></th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">Fm</th>
                                            <th style="border-bottom:1px solid #000000">Ft</th>
                                            <th style="border-bottom:1px solid #000000">C</th>
                                            <th class="border-right" style="border-bottom:1px solid #000000">I</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">Fm</th>
                                            <th style="border-bottom:1px solid #000000">Ft</th>
                                            <th style="border-bottom:1px solid #000000">C</th>
                                            <th class="border-right" style="border-bottom:1px solid #000000">I</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">Fm</th>
                                            <th style="border-bottom:1px solid #000000">Ft</th>
                                            <th style="border-bottom:1px solid #000000">C</th>
                                            <th class="border-right" style="border-bottom:1px solid #000000">I</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">Fm</th>
                                            <th style="border-bottom:1px solid #000000">Ft</th>
                                            <th style="border-bottom:1px solid #000000">C</th>
                                            <th class="border-right" style="border-bottom:1px solid #000000">I</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">Fm</th>
                                            <th style="border-bottom:1px solid #000000">Ft</th>
                                            <th style="border-bottom:1px solid #000000">C</th>
                                            <th style="border-bottom:1px solid #000000">I</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td rowspan="9" colspan="1" class="vertical-text border-right"
                                                style="border-bottom:1px solid #000000">Uncons Model</td>
                                            <td colspan="2" class="border-right">CTlog</td>
                                            <td>69</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>66</td>
                                            <td>0</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>62</td>
                                            <td>5</td>
                                            <td>2</td>
                                            <td>0</td>
                                            <td class="border-right">13.5</td>
                                            <td>50</td>
                                            <td>11</td>
                                            <td>8</td>
                                            <td>0</td>
                                            <td class="border-right">8.1</td>
                                            <td>42</td>
                                            <td>15</td>
                                            <td>12</td>
                                            <td>0</td>
                                            <td>5.1</td>
                                        </tr>

                                        <tr>


                                            <td colspan="2" class="border-right">coffee4j</td>
                                            <td>64</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>5</td>
                                            <td class="border-right">26.4</td>
                                            <td>62</td>
                                            <td>0</td>
                                            <td>2</td>
                                            <td>5</td>
                                            <td class="border-right">26.4</td>
                                            <td>58</td>
                                            <td>0</td>
                                            <td>6</td>
                                            <td>5</td>
                                            <td class="border-right">13.5</td>
                                            <td>56</td>
                                            <td>0</td>
                                            <td>8</td>
                                            <td>2</td>
                                            <td class="border-right">12.0</td>
                                            <td>50</td>
                                            <td>0</td>
                                            <td>14</td>
                                            <td>5</td>
                                            <td>8.3</td>
                                        </tr>

                                        <tr>

                                            <td colspan="2" class="border-right">CAgen</td>
                                            <td>66</td>
                                            <td>0</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>65</td>
                                            <td>0</td>
                                            <td>4</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>64</td>
                                            <td>0</td>
                                            <td>5</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>62</td>
                                            <td>0</td>
                                            <td>7</td>
                                            <td>0</td>
                                            <td class="border-right">13.5</td>
                                            <td>56</td>
                                            <td>0</td>
                                            <td>13</td>
                                            <td>0</td>
                                            <td>12.0</td>
                                        </tr>

                                        <tr>

                                            <td colspan="2" class="border-right">medici</td>
                                            <td>66</td>
                                            <td>0</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>64</td>
                                            <td>0</td>
                                            <td>5</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>58</td>
                                            <td>5</td>
                                            <td>6</td>
                                            <td>0</td>
                                            <td class="border-right">12.0</td>
                                            <td>45</td>
                                            <td>7</td>
                                            <td>17</td>
                                            <td>0</td>
                                            <td class="border-right">6.4</td>
                                            <td>39</td>
                                            <td>14</td>
                                            <td>16</td>
                                            <td>0</td>
                                            <td>4.8</td>
                                        </tr>
                                        <tr>

                                            <td colspan="2" class="border-right">JCUnit</td>
                                            <td>67</td>
                                            <td>0</td>
                                            <td>2</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>63</td>
                                            <td>0</td>
                                            <td>6</td>
                                            <td>0</td>
                                            <td class="border-right">13.5</td>
                                            <td>51</td>
                                            <td>0</td>
                                            <td>18</td>
                                            <td>0</td>
                                            <td class="border-right">7.7</td>
                                            <td>45</td>
                                            <td>1</td>
                                            <td>23</td>
                                            <td>0</td>
                                            <td class="border-right">5.2</td>
                                            <td>39</td>
                                            <td>4</td>
                                            <td>26</td>
                                            <td>0</td>
                                            <td>4.6</td>
                                        </tr>
                                        <tr>


                                            <td colspan="2" class="border-right">ACTS</td>
                                            <td>69</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>69</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>68</td>
                                            <td>0</td>
                                            <td>1</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>63</td>
                                            <td>1</td>
                                            <td>5</td>
                                            <td>0</td>
                                            <td class="border-right">13.5</td>
                                            <td>62</td>
                                            <td>1</td>
                                            <td>6</td>
                                            <td>0</td>
                                            <td>12.0</td>
                                        </tr>
                                        <tr>


                                            <td colspan="2" class="border-right">Tcases</td>
                                            <td>66</td>
                                            <td>0</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>55</td>
                                            <td>2</td>
                                            <td>12</td>
                                            <td>0</td>
                                            <td class="border-right">9.9</td>
                                            <td>42</td>
                                            <td>6</td>
                                            <td>21</td>
                                            <td>0</td>
                                            <td class="border-right">5.2</td>
                                            <td>30</td>
                                            <td>12</td>
                                            <td>27</td>
                                            <td>0</td>
                                            <td class="border-right">4.3</td>
                                            <td>27</td>
                                            <td>19</td>
                                            <td>23</td>
                                            <td>0</td>
                                            <td>4.3</td>
                                        </tr>
                                        <tr>


                                            <td colspan="2" class="border-right">PICT</td>
                                            <td>69</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>65</td>
                                            <td>0</td>
                                            <td>4</td>
                                            <td>0</td>
                                            <td class="border-right">26.4</td>
                                            <td>62</td>
                                            <td>0</td>
                                            <td>7</td>
                                            <td>0</td>
                                            <td class="border-right">13.5</td>
                                            <td>47</td>
                                            <td>1</td>
                                            <td>21</td>
                                            <td>0</td>
                                            <td class="border-right">6.4</td>
                                            <td>42</td>
                                            <td>6</td>
                                            <td>21</td>
                                            <td>0</td>
                                            <td>5.1</td>
                                        </tr>
                                        <tr style="border-bottom:1px solid #000000;">
                                            <td colspan="2" class="border-right">Jenny</td>
                                            <td>60</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>9</td>
                                            <td class="border-right">26.4</td>
                                            <td>60</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>9</td>
                                            <td class="border-right">26.4</td>
                                            <td>58</td>
                                            <td>0</td>
                                            <td>2</td>
                                            <td>9</td>
                                            <td class="border-right">25.3</td>
                                            <td>55</td>
                                            <td>0</td>
                                            <td>5</td>
                                            <td>9</td>
                                            <td class="border-right">13.5</td>
                                            <td>42</td>
                                            <td>0</td>
                                            <td>18</td>
                                            <td>9</td>
                                            <td>6.4</td>
                                        </tr>

                                        <tr>
                                            <td rowspan="9" colspan="1" class="vertical-text border-right"
                                                style="border-bottom:1px solid #000000">Cons Model</td>
                                            <td colspan="2" class="border-right">CTlog</td>
                                            <td>153</td>
                                            <td>3</td>
                                            <td>1</td>
                                            <td>0</td>
                                            <td class="border-right">inf</td>
                                            <td>143</td>
                                            <td>10</td>
                                            <td>4</td>
                                            <td>0</td>
                                            <td class="border-right">61.7</td>
                                            <td>124</td>
                                            <td>20</td>
                                            <td>13</td>
                                            <td>0</td>
                                            <td class="border-right">18.4</td>
                                            <td>86</td>
                                            <td>44</td>
                                            <td>27</td>
                                            <td>0</td>
                                            <td class="border-right">10.5</td>
                                            <td>66</td>
                                            <td>62</td>
                                            <td>29</td>
                                            <td>0</td>
                                            <td>6.9</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">coffee4j</td>
                                            <td>122</td>
                                            <td>0</td>
                                            <td>17</td>
                                            <td>18</td>
                                            <td class="border-right">61.7</td>
                                            <td>110</td>
                                            <td>0</td>
                                            <td>28</td>
                                            <td>19</td>
                                            <td class="border-right">23.5</td>
                                            <td>93</td>
                                            <td>0</td>
                                            <td>45</td>
                                            <td>19</td>
                                            <td class="border-right">14.1</td>
                                            <td>80</td>
                                            <td>0</td>
                                            <td>58</td>
                                            <td>19</td>
                                            <td class="border-right">9.1</td>
                                            <td>68</td>
                                            <td>0</td>
                                            <td>64</td>
                                            <td>25</td>
                                            <td>7.4</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">CAgen</td>
                                            <td>146</td>
                                            <td>0</td>
                                            <td>11</td>
                                            <td>0</td>
                                            <td class="border-right">inf</td>
                                            <td>137</td>
                                            <td>0</td>
                                            <td>20</td>
                                            <td>0</td>
                                            <td class="border-right">163.8</td>
                                            <td>123</td>
                                            <td>0</td>
                                            <td>34</td>
                                            <td>0</td>
                                            <td class="border-right">31.3</td>
                                            <td>100</td>
                                            <td>1</td>
                                            <td>56</td>
                                            <td>0</td>
                                            <td class="border-right">20.5</td>
                                            <td>64</td>
                                            <td>1</td>
                                            <td>92</td>
                                            <td>0</td>
                                            <td>10.5</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">medici</td>
                                            <td>146</td>
                                            <td>7</td>
                                            <td>4</td>
                                            <td>0</td>
                                            <td class="border-right">163.8</td>
                                            <td>134</td>
                                            <td>10</td>
                                            <td>13</td>
                                            <td>0</td>
                                            <td class="border-right">20.5</td>
                                            <td>103</td>
                                            <td>20</td>
                                            <td>34</td>
                                            <td>0</td>
                                            <td class="border-right">14.1</td>
                                            <td>69</td>
                                            <td>36</td>
                                            <td>52</td>
                                            <td>0</td>
                                            <td class="border-right">6.9</td>
                                            <td>63</td>
                                            <td>53</td>
                                            <td>41</td>
                                            <td>0</td>
                                            <td>6.3</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">JCUnit</td>
                                            <td>113</td>
                                            <td>4</td>
                                            <td>34</td>
                                            <td>6</td>
                                            <td class="border-right">61.7</td>
                                            <td>97</td>
                                            <td>14</td>
                                            <td>40</td>
                                            <td>6</td>
                                            <td class="border-right">16.4</td>
                                            <td>65</td>
                                            <td>16</td>
                                            <td>69</td>
                                            <td>7</td>
                                            <td class="border-right">7.3</td>
                                            <td>60</td>
                                            <td>7</td>
                                            <td>84</td>
                                            <td>6</td>
                                            <td class="border-right">5.5</td>
                                            <td>53</td>
                                            <td>6</td>
                                            <td>89</td>
                                            <td>9</td>
                                            <td>5.4</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">ACTS</td>
                                            <td>142</td>
                                            <td>1</td>
                                            <td>14</td>
                                            <td>0</td>
                                            <td class="border-right">inf</td>
                                            <td>141</td>
                                            <td>1</td>
                                            <td>15</td>
                                            <td>0</td>
                                            <td class="border-right">163.8</td>
                                            <td>135</td>
                                            <td>1</td>
                                            <td>21</td>
                                            <td>0</td>
                                            <td class="border-right">31.3</td>
                                            <td>125</td>
                                            <td>1</td>
                                            <td>31</td>
                                            <td>0</td>
                                            <td class="border-right">18.4</td>
                                            <td>112</td>
                                            <td>2</td>
                                            <td>43</td>
                                            <td>0</td>
                                            <td>12.8</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">Tcases</td>
                                            <td>33</td>
                                            <td>0</td>
                                            <td>15</td>
                                            <td>109</td>
                                            <td class="border-right">22.5</td>
                                            <td>23</td>
                                            <td>2</td>
                                            <td>22</td>
                                            <td>110</td>
                                            <td class="border-right">8.2</td>
                                            <td>15</td>
                                            <td>11</td>
                                            <td>21</td>
                                            <td>110</td>
                                            <td class="border-right">4.8</td>
                                            <td>12</td>
                                            <td>16</td>
                                            <td>19</td>
                                            <td>110</td>
                                            <td class="border-right">4.0</td>
                                            <td>10</td>
                                            <td>22</td>
                                            <td>15</td>
                                            <td>110</td>
                                            <td>4.0</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">PICT</td>
                                            <td>127</td>
                                            <td>0</td>
                                            <td>30</td>
                                            <td>0</td>
                                            <td class="border-right">inf</td>
                                            <td>122</td>
                                            <td>1</td>
                                            <td>34</td>
                                            <td>0</td>
                                            <td class="border-right">61.7</td>
                                            <td>104</td>
                                            <td>2</td>
                                            <td>51</td>
                                            <td>0</td>
                                            <td class="border-right">16.4</td>
                                            <td>80</td>
                                            <td>6</td>
                                            <td>71</td>
                                            <td>0</td>
                                            <td class="border-right">10.5</td>
                                            <td>65</td>
                                            <td>10</td>
                                            <td>82</td>
                                            <td>0</td>
                                            <td>6.9</td>
                                        </tr>
                                        <tr style="border-bottom:1px solid #000000;">
                                            <td colspan="2" style="border-right:1px solid #000000">Jenny</td>
                                            <td>31</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td>126</td>
                                            <td style="border-right:1px solid #000000;">55.3</td>
                                            <td>27</td>
                                            <td>0</td>
                                            <td>1</td>
                                            <td>129</td>
                                            <td style="border-right:1px solid #000000;">22.5</td>
                                            <td>25</td>
                                            <td>0</td>
                                            <td>2</td>
                                            <td>130</td>
                                            <td style="border-right:1px solid #000000;">16.4</td>
                                            <td>23</td>
                                            <td>0</td>
                                            <td>4</td>
                                            <td>130</td>
                                            <td style="border-right:1px solid #000000;">16.4</td>
                                            <td>18</td>
                                            <td>0</td>
                                            <td>9</td>
                                            <td>130</td>
                                            <td>8.2</td>
                                        </tr>

                                    </tbody>
                                </table>
                            </div>

                        </div>



                    </div>
                </div>

                <div class="col-12">
                    <div class="card mb-4">
                        <div class="card-body pb-0 mb-4">
                            <!-- 表2 9个工具的对比结果 -->
                            <h6 class="text-center">Table 2:
                                Comparative results of 9 Greedy tools on three metrics: Covering Array <b>S</b>ize,
                                <b>T</b>ime
                                required for generating covering array, and comprehensive evaluation.
                            </h6>
                            <p>Note:</p>
                            <p><b>S</b> indicates the num of times tool's CA size is less than or equal to the CA size
                                generated by other tools. </p>
                            <p><b>T</b> indicates the num of times tool's required time for generating ca is less than or
                                equal to that of other tools. </p>
                            <p><b>B</b> indicates the num of times the tool's performance is better than or equal to other
                                tools in both <b>Size</b> and <b>Time</b> metrics. </p>
                            <div class='table-responsive'>
                                <table class="table table-sm ">
                                    <thead>
                                        <tr>
                                            <th colspan="1"></th>
                                            <th colspan="2"></th>
                                            <th colspan="3" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">2-way</th>
                                            <th colspan="3" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">3-way</th>
                                            <th colspan="3" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">4-way</th>
                                            <th colspan="3" class="border-right border-left "
                                                style="border-bottom:1px solid #000000">5-way</th>
                                            <th colspan="3" class="border-left" style="border-bottom:1px solid #000000">
                                                6-way</th>
                                        </tr>
                                        <tr>
                                            <th colspan="3" style="border-bottom:1px solid #000000"></th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">T</th>
                                            <th style="border-bottom:1px solid #000000">B</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">T</th>
                                            <th style="border-bottom:1px solid #000000">B</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">T</th>
                                            <th style="border-bottom:1px solid #000000">B</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">T</th>
                                            <th style="border-bottom:1px solid #000000">B</th>
                                            <th class="border-left" style="border-bottom:1px solid #000000">S</th>
                                            <th style="border-bottom:1px solid #000000">T</th>
                                            <th style="border-bottom:1px solid #000000">B</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <tr>
                                            <td rowspan="9" colspan="1" class="vertical-text border-right"
                                                style="border-bottom:1px solid #000000">Uncons Model</td>
                                            <td colspan="2" class="border-right">CTlog</td>
                                            <td>18</td>
                                            <td>0</td>
                                            <td class="border-right">3</td>
                                            <td>4</td>
                                            <td>0</td>
                                            <td class="border-right">2</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">1</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">3</td>
                                            <td>5</td>
                                            <td>0</td>
                                            <td>4</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">coffee4j</td>
                                            <td>5</td>
                                            <td>57</td>
                                            <td class="border-right">3</td>
                                            <td>0</td>
                                            <td>39</td>
                                            <td class="border-right">1</td>
                                            <td>1</td>
                                            <td>26</td>
                                            <td class="border-right">1</td>
                                            <td>3</td>
                                            <td>22</td>
                                            <td class="border-right">3</td>
                                            <td>7</td>
                                            <td>22</td>
                                            <td>8</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">CAgen</td>
                                            <td>38</td>
                                            <td>61</td>
                                            <td class="border-right">43</td>
                                            <td>12</td>
                                            <td>55</td>
                                            <td class="border-right">31</td>
                                            <td>5</td>
                                            <td>48</td>
                                            <td class="border-right">34</td>
                                            <td>5</td>
                                            <td>38</td>
                                            <td class="border-right">31</td>
                                            <td>20</td>
                                            <td>31</td>
                                            <td>37</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">medici</td>
                                            <td>34</td>
                                            <td>44</td>
                                            <td class="border-right">39</td>
                                            <td>5</td>
                                            <td>6</td>
                                            <td class="border-right">22</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">18</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">14</td>
                                            <td>15</td>
                                            <td>1</td>
                                            <td>14</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">JcUnit</td>
                                            <td>14</td>
                                            <td>53</td>
                                            <td class="border-right">12</td>
                                            <td>4</td>
                                            <td>29</td>
                                            <td class="border-right">5</td>
                                            <td>2</td>
                                            <td>12</td>
                                            <td class="border-right">5</td>
                                            <td>4</td>
                                            <td>9</td>
                                            <td class="border-right">8</td>
                                            <td>16</td>
                                            <td>11</td>
                                            <td>13</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">ACTS</td>
                                            <td>26</td>
                                            <td>69</td>
                                            <td class="border-right">38</td>
                                            <td>4</td>
                                            <td>69</td>
                                            <td class="border-right">24</td>
                                            <td>5</td>
                                            <td>66</td>
                                            <td class="border-right">30</td>
                                            <td>4</td>
                                            <td>60</td>
                                            <td class="border-right">36</td>
                                            <td>18</td>
                                            <td>60</td>
                                            <td>47</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">Tcases</td>
                                            <td>1</td>
                                            <td>3</td>
                                            <td class="border-right">2</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">1</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">0</td>
                                            <td>0</td>
                                            <td>0</td>
                                            <td class="border-right">0</td>
                                            <td>5</td>
                                            <td>0</td>
                                            <td>0</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">PICT</td>
                                            <td>12</td>
                                            <td>65</td>
                                            <td class="border-right">30</td>
                                            <td>3</td>
                                            <td>47</td>
                                            <td class="border-right">18</td>
                                            <td>0</td>
                                            <td>32</td>
                                            <td class="border-right">17</td>
                                            <td>0</td>
                                            <td>16</td>
                                            <td class="border-right">18</td>
                                            <td>5</td>
                                            <td>12</td>
                                            <td>13</td>
                                        </tr>

                                        <tr style="border-bottom:1px solid #000000;">
                                            <td colspan="2" class="border-right">Jenny</td>
                                            <td>7</td>
                                            <td>60</td>
                                            <td class="border-right">7</td>
                                            <td>1</td>
                                            <td>50</td>
                                            <td class="border-right">5</td>
                                            <td>0</td>
                                            <td>34</td>
                                            <td class="border-right">7</td>
                                            <td>0</td>
                                            <td>28</td>
                                            <td class="border-right">10</td>
                                            <td>5</td>
                                            <td>24</td>
                                            <td>9</td>
                                        </tr>

                                        <tr>
                                            <td rowspan="9" colspan="1" class="vertical-text border-right"
                                                style="border-bottom:1px solid #000000">Cons Model</td>
                                            <td colspan="2" class="border-right">CTlog</td>
                                            <td>30</td>
                                            <td>29</td>
                                            <td class="border-right">46</td>
                                            <td>14</td>
                                            <td>22</td>
                                            <td class="border-right">63</td>
                                            <td>15</td>
                                            <td>9</td>
                                            <td class="border-right">52</td>
                                            <td>20</td>
                                            <td>3</td>
                                            <td class="border-right">20</td>
                                            <td>34</td>
                                            <td>1</td>
                                            <td>6</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">coffee4j</td>
                                            <td>7</td>
                                            <td>41</td>
                                            <td class="border-right">6</td>
                                            <td>8</td>
                                            <td>18</td>
                                            <td class="border-right">4</td>
                                            <td>10</td>
                                            <td>6</td>
                                            <td class="border-right">3</td>
                                            <td>20</td>
                                            <td>4</td>
                                            <td class="border-right">7</td>
                                            <td>32</td>
                                            <td>9</td>
                                            <td>11</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">CAgen</td>
                                            <td>44</td>
                                            <td>85</td>
                                            <td class="border-right">86</td>
                                            <td>16</td>
                                            <td>61</td>
                                            <td class="border-right">65</td>
                                            <td>13</td>
                                            <td>47</td>
                                            <td class="border-right">50</td>
                                            <td>17</td>
                                            <td>41</td>
                                            <td class="border-right">43</td>
                                            <td>20</td>
                                            <td>32</td>
                                            <td>34</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">medici</td>
                                            <td>32</td>
                                            <td>68</td>
                                            <td class="border-right">72</td>
                                            <td>15</td>
                                            <td>5</td>
                                            <td class="border-right">49</td>
                                            <td>21</td>
                                            <td>1</td>
                                            <td class="border-right">43</td>
                                            <td>29</td>
                                            <td>1</td>
                                            <td class="border-right">21</td>
                                            <td>41</td>
                                            <td>3</td>
                                            <td>10</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">JCUnit</td>
                                            <td>9</td>
                                            <td>46</td>
                                            <td class="border-right">6</td>
                                            <td>8</td>
                                            <td>13</td>
                                            <td class="border-right">8</td>
                                            <td>9</td>
                                            <td>5</td>
                                            <td class="border-right">9</td>
                                            <td>26</td>
                                            <td>5</td>
                                            <td class="border-right">8</td>
                                            <td>34</td>
                                            <td>10</td>
                                            <td>11</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">ACTS</td>
                                            <td>20</td>
                                            <td>95</td>
                                            <td class="border-right">40</td>
                                            <td>14</td>
                                            <td>108</td>
                                            <td class="border-right">88</td>
                                            <td>16</td>
                                            <td>115</td>
                                            <td class="border-right">98</td>
                                            <td>32</td>
                                            <td>107</td>
                                            <td class="border-right">100</td>
                                            <td>48</td>
                                            <td>96</td>
                                            <td>95</td>
                                        </tr>

                                        <tr>
                                            <td colspan="2" class="border-right">Tcases</td>
                                            <td>0</td>
                                            <td>1</td>
                                            <td class="border-right">1</td>
                                            <td>1</td>
                                            <td>0</td>
                                            <td class="border-right">0</td>
                                            <td>2</td>
                                            <td>0</td>
                                            <td class="border-right">0</td>
                                            <td>3</td>
                                            <td>0</td>
                                            <td class="border-right">0</td>
                                            <td>7</td>
                                            <td>0</td>
                                            <td>0</td>
                                        </tr>
                                        <tr>
                                            <td colspan="2" class="border-right">PICT</td>
                                            <td>16</td>
                                            <td>100</td>
                                            <td class="border-right">54</td>
                                            <td>11</td>
                                            <td>66</td>
                                            <td class="border-right">46</td>
                                            <td>12</td>
                                            <td>28</td>
                                            <td class="border-right">34</td>
                                            <td>21</td>
                                            <td>12</td>
                                            <td class="border-right">31</td>
                                            <td>535</td>
                                            <td>11</td>
                                            <td>25</td>
                                        </tr>

                                        <tr style="border-bottom:1px solid #000000;">
                                            <td colspan="2" style="border-right:1px solid #000000;">Jenny</td>
                                            <td>1</td>
                                            <td>31</td>
                                            <td style="border-right:1px solid #000000;">2</td>
                                            <td>0</td>
                                            <td>20</td>
                                            <td style="border-right:1px solid #000000;">4</td>
                                            <td>1</td>
                                            <td>13</td>
                                            <td style="border-right:1px solid #000000;">10</td>
                                            <td>3</td>
                                            <td>12</td>
                                            <td style="border-right:1px solid #000000;">11</td>
                                            <td>6</td>
                                            <td>5</td>
                                            <td>9</td>
                                        </tr>




                                    </tbody>
                                </table>
                            </div>

                        </div>



                    </div>
                </div>

                <!-- 其他一些图表 -->
                <div></div>
            </div>

            <Foot></Foot>
        </div>
    </main>
</template>

<script  setup>
import { reactive, onMounted, ref } from 'vue';
import ArgonButton from '../CustomizedComponents/ArgonButton.vue';
import { ElLoading } from 'element-plus'
import { useRouter } from 'vue-router';
import Foot from '../CustomizedComponents/Foot.vue';
import Navbar from '../CustomizedComponents/Navbar.vue';
import SideNav from './components/SideNav.vue'
import { searchModel } from './commonFunction'
import { useModuleStore } from '../store/module';
import { useUserStore } from '../store/userStore';
import { usePaperInfoStore } from '../store/RepositoryStore/paperinfoStore'
import { usePaginationStore } from '../store/RepositoryStore/paginationStore'
import pinia from '../store/store';

const router = useRouter()

const PaginationStore = usePaginationStore(pinia)
const PaperInfoStore = usePaperInfoStore(pinia)
const moduleStore = useModuleStore(pinia)
const userStore = useUserStore(pinia)

onMounted(() => {

})
</script>


<style scoped>
.table th,
.table td {
    text-align: center;
    vertical-align: middle !important;
}

table {
    border-collapse: collapse;
}



.vertical-text {
    /* 使用 transform 属性实现竖直显示 */
    transform: rotate(-90deg);
    /* 文本居中 */
    text-align: center;
    /* 防止换行 */
    white-space: nowrap;
}

/* 添加边框的自定义类 */
.border-top {
    border-top: 1px solid black;
}

.border-bottom {
    border-bottom: 1px solid #000000
}

.border-left {
    border-left: 1px solid black;
}

.border-right {
    border-right: 1px solid black;
}
</style>
