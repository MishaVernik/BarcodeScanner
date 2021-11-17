using System;
using System.IO;
using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Azure.WebJobs;
using Microsoft.Azure.WebJobs.Extensions.Http;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using BarcodeFAPI.Context;
using System.Linq;
using BarcodeFAPI.Model;
using System.Text.Json.Nodes;

namespace BarcodeFAPI
{
    public partial class HttpBarcodeTrigger
    {
        private readonly barcode_fapiContext context;

        public HttpBarcodeTrigger(barcode_fapiContext context)
        {
            this.context = context;
        }

        [FunctionName("TestNoAuth")]
        public IActionResult TestNoAuth(
   [HttpTrigger(AuthorizationLevel.Anonymous, "get", Route = null)] HttpRequest req)
        {
            return new OkObjectResult("It Works without AUTH!");
        }

        [FunctionName("TestAuth")]
        public IActionResult TestAuth(
   [HttpTrigger(AuthorizationLevel.Function, "get", Route = null)] HttpRequest req)
        {
            return new OkObjectResult("It Works with AUTH");
        }

        [FunctionName("GetListOfGeneratedItems")]
        public IActionResult GetListOfGeneratedItems(
           [HttpTrigger(AuthorizationLevel.Function, "get", Route = null)] HttpRequest req,
           ILogger log)
        {
            log.LogInformation("C# HTTP trigger function processed a request.");


            string responseMessage = String.Join("\n", context.GeneratedLogs.Select(r => r.Id.ToString() + " " + r.BarCode.ToString() + " " + r.CreatedDateTime.ToString()));

            return new JsonResult(responseMessage);
        }
        [FunctionName("GetListOfScannedItems")]
        public IActionResult GetListOfScannedItems(
           [HttpTrigger(AuthorizationLevel.Function, "get", Route = null)] HttpRequest req,
           ILogger log)
        {
            log.LogInformation("C# HTTP trigger function processed a request.");


            string responseMessage = String.Join("\n", context.ScannedLogs.Select(r => r.Id.ToString() + " " + r.BarCode.ToString() + " " + r.CreatedDateTime.ToString()));

            return new JsonResult(responseMessage);
        }

        [FunctionName("PostGeneratedItem")]
        public async Task<IActionResult> PostGeneratedItem(
            [HttpTrigger(AuthorizationLevel.Function,"get", "post", Route = null)] HttpRequest req,
            ILogger log)
        {
            log.LogInformation("C# HTTP trigger function processed a request.");

            string name = req.Query["name"];
            try
            {
                string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
                PostData myClass = JsonConvert.DeserializeObject<PostData>(requestBody);
                //dynamic data = JsonConvert.DeserializeObject(requestBody);
                name = name ?? myClass?.name;
            }
            catch (Exception ex)
            {
                return new BadRequestObjectResult(ex);
            }
          
          

            //string responseMessage = string.IsNullOrEmpty(name)
            //    ? "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response."
            //    : $"Hello, {name}. This HTTP triggered function executed successfully.";

            context.GeneratedLogs.Add(new GeneratedLog(name, DateTime.Now));
            await context.SaveChangesAsync();

            return new JsonResult(name);
        }

        [FunctionName("PostScannedItem")]
        public async Task<IActionResult> PostScannedItem(
           [HttpTrigger(AuthorizationLevel.Function, "get", "post", Route = null)] HttpRequest req,
           ILogger log)
        {
            log.LogInformation("C# HTTP trigger function processed a request.");

            string name = req.Query["name"];
            try
            {
                string requestBody = await new StreamReader(req.Body).ReadToEndAsync();
                PostData myClass = JsonConvert.DeserializeObject<PostData>(requestBody);
                //dynamic data = JsonConvert.DeserializeObject(requestBody);
                name = name ?? myClass?.name;
            }
            catch (Exception ex)
            {
                return new BadRequestObjectResult(ex);
            }



            //string responseMessage = string.IsNullOrEmpty(name)
            //    ? "This HTTP triggered function executed successfully. Pass a name in the query string or in the request body for a personalized response."
            //    : $"Hello, {name}. This HTTP triggered function executed successfully.";

            context.ScannedLogs.Add(new ScannedLog(name, DateTime.Now));
            await context.SaveChangesAsync();

            return new JsonResult(name);
        }
    }
}
