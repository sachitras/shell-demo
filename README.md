<h1>Shell Application</h1>
<p>This shell application is used to create and provision core capabilities, 
and the order of execution is listed below with more details.</p>

<h2>Requirements</h2>
<p>The application will be run using a command line tool</p>
<hr>

<h2>Available commands</h2>
<h3><b>1. ``px tenant create``</b></h3>
<h4>This will create a new tenant with a given name by the following parameter.</h4>
<h5>Arguments:</h5>
<p>--name : Name of the Tenant (Mandatory)</p>
<hr>

<h3><b>2. ``px product-family create``</b></h3>
<h4>This will create a new product family with a given name.
The newly created product family will be assigned a tenant and a smart contract URL using the following arguments.</h4>
<h5>Arguments:</h5>
<p>--name : Name of the Product Family (Mandatory)</p>
<p>--tenant : Tenant Id (Mandatory)</p>
<p>--smart-contract : Smart contract URL (Optional)</p>
<hr>

<h3><b>3. ``px capability add``</b></h3>
<h4>This will add capabilities to the product family.</h4>
<h5>Arguments:</h5>
<p>--select : Comma separated capability names (Mandatory)</p>
<p>--product-family : Product family Id (Mandatory)</p>
<hr>

<h3><b>4. ``px capability configure``</b></h3>
<h4>This will configure the added capabilities.</h4>
<h5>Arguments:</h5>
<p> : Capability name : Note - No argument name (Mandatory)</p>
<p>--product-family : Product family Id (Mandatory)</p>
<p>--api-ext : API extension URL (Optional)</p>
<p>--adaptor-ext : Adaptor extension URL (Optional)</p>

> Note: In the above command, either api-ext or adaptor-ext should be present.
<hr>

<h3><b>5. ``px provision``</b></h3>
<h4>This will provision the product family</h4>
<h5>Arguments:</h5>
<p>--product-family : Product family Id (Mandatory)</p>
<p>--env : Environment name (Mandatory)</p>


