<p>给定两个大小相等的数组&nbsp;<code>A</code>&nbsp;和&nbsp;<code>B</code>，A 相对于 B 的<em>优势</em>可以用满足&nbsp;<code>A[i] &gt; B[i]</code>&nbsp;的索引 <code>i</code>&nbsp;的数目来描述。</p>

<p>返回&nbsp;<code>A</code>&nbsp;的<strong>任意</strong>排列，使其相对于 <code>B</code>&nbsp;的优势最大化。</p>

<p>&nbsp;</p>

<p><strong>示例 1：</strong></p>

<pre><strong>输入：</strong>A = [2,7,11,15], B = [1,10,4,11]
<strong>输出：</strong>[2,11,7,15]
</pre>

<p><strong>示例 2：</strong></p>

<pre><strong>输入：</strong>A = [12,24,8,32], B = [13,25,32,11]
<strong>输出：</strong>[24,32,8,12]
</pre>

<p>&nbsp;</p>

<p><strong>提示：</strong></p>

<ol>
	<li><code>1 &lt;= A.length = B.length &lt;= 10000</code></li>
	<li><code>0 &lt;= A[i] &lt;= 10^9</code></li>
	<li><code>0 &lt;= B[i] &lt;= 10^9</code></li>
</ol>
<div><div>Related Topics</div><div><li>贪心</li><li>数组</li><li>排序</li></div></div>\n<div><li>👍 137</li><li>👎 0</li></div>